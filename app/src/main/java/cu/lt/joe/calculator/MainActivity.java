package cu.lt.joe.calculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import cu.lt.joe.calculator.adapters.OperationsHistoryAdapter;
import cu.lt.joe.calculator.data_structures.Stack;
import cu.lt.joe.calculator.db.HistoryDatabaseHandler;
import cu.lt.joe.calculator.exceptions.InfinityResultException;
import cu.lt.joe.calculator.exceptions.NotNumericResultException;
import cu.lt.joe.calculator.exceptions.UnbalancedParenthesesException;
import cu.lt.joe.calculator.models.HistoryItem;
import cu.lt.joe.calculator.utils.RPN;
import cu.lt.joe.calculator.utils.SY;
import cu.lt.joe.calculator.R;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener
{
    private boolean solved;
    private EditText screen;
    private HistoryDatabaseHandler operations_records_handler;
    private ListView history_lv;
    private DrawerLayout history_drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        history_drawer = findViewById(R.id.history_drawer);
        history_lv = findViewById(R.id.history_lv);
        history_lv.setDividerHeight(0);
        screen = findViewById(R.id.screen);
        screen.setShowSoftInputOnFocus(false);
        screen.setCursorVisible(false);
        operations_records_handler = new HistoryDatabaseHandler(this, new HistoryDatabaseHandler.OnDataTransactionListener()
            {
                @Override
                public void onDataChanged()
                {
                    history_lv.setAdapter(new OperationsHistoryAdapter(MainActivity.this, operations_records_handler.getOperationsHistory()));
                }
            });
        history_lv.setAdapter(new OperationsHistoryAdapter(MainActivity.this, operations_records_handler.getOperationsHistory()));
        screen.setCustomSelectionActionModeCallback(new ActionMode.Callback()
            {
                @Override
                public boolean onCreateActionMode(ActionMode p1, Menu p2)
                {
                    return false;
                }
                @Override
                public boolean onPrepareActionMode(ActionMode p1, Menu p2)
                {
                    return false;
                }
                @Override
                public boolean onActionItemClicked(ActionMode p1, MenuItem p2)
                {
                    return false;
                }
                @Override
                public void onDestroyActionMode(ActionMode p1)
                {}
            });

        screen.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
                {}

                @Override
                public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
                {
                    String texto = screen.getText().toString();
                    if (solved && !texto.isEmpty())
                    {
                        solved = false;
                        if ((texto.charAt(texto.length() - 1) + "").equals("(") || (texto.charAt(texto.length() - 1) + "").equals("."))
                        {
                            screen.setText(texto);
                        }
                        else if (!isOperator(texto.charAt(texto.length() - 1) + ""))
                        {
                            texto = texto.substring(texto.length() - 1);
                            screen.setText(texto);
                        }
                    }
                    else if (texto.contains(")("))
                    {
                        screen.setText(texto.replace(")(", ")×("));
                    }
                    else if (texto.contains("(+") || texto.contains("(×") || texto.contains("(÷") || texto.contains("(%"))
                    {
                        screen.setText(texto.replace("(+", "(").replace("(×", "(").replace("(÷", "(").replace("(%", "("));
                    }
                    else if (texto.contains("()"))
                    {
                        screen.setText(texto.replace("()", "(1)"));
                    }
                }
                @Override
                public void afterTextChanged(Editable p1)
                {}
            });

        screen.setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                        return false;
                    return true;
                }
            });

        history_lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
                {
                    history_drawer.closeDrawer(GravityCompat.END);
                    solved = false;
                    screen.setText(((HistoryItem)p1.getItemAtPosition(p3)).getResult());
                }
            });
        history_lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
                {
                    showHistoryItemPopupMenu((HistoryItem)p1.getItemAtPosition(p3), p2);
                    return true;
                }
            });
        findViewById(R.id.delete).getLayoutParams().width = findViewById(R.id.divide).getLayoutParams().width;
        findViewById(R.id.delete).setOnLongClickListener(this);
        screen.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View p1)
    {
        String text = screen.getText().toString();
        switch (p1.getId())
        {
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
                {
                    String number = ((Button)p1).getText().toString();
                    if (!text.isEmpty() && text.charAt(text.length() - 1) == ')')
                    {
                        screen.setText(text + "×" + number);
                    }
                    else
                    {
                        screen.setText(text + number);
                    }
                    screen.setSelection(screen.getText().length());
                    break;
                }
            case R.id.add:
            case R.id.subtract:
            case R.id.multiply:
            case R.id.divide:
            case R.id.modDiv:
                {
                    String operator = ((Button)p1).getText().toString();
                    if (!text.isEmpty() && !isOperator(text.charAt(text.length() - 1) + ""))
                    {
                        screen.setText(text + operator);
                        screen.setSelection(screen.getText().length());
                    }
                    else if (!text.isEmpty() && isOperator(text.charAt(text.length() - 1) + ""))
                    {
                        text = text.substring(0, text.length() - 1);
                        screen.setText(text + operator);
                        screen.setSelection(screen.getText().length());
                    }
                    break;
                }
            case R.id.comma:
                {
                    if (!text.isEmpty())
                    {
                        if (isNumber(text.charAt(text.length() - 1) + ""))
                        {
                            String arr[] = text.replace("(", "( ").replace("+", " + ").replace("-", " - ").replace("×", " × ").replace("÷", " ÷ ").replace("%", " % ").replace(")", " )").replace("n", "-").trim().split(" ");
                            if (!arr[arr.length - 1].contains("."))
                            {
                                screen.setText(screen.getText().toString() + ".");
                                screen.setSelection(screen.getText().length());
                            }
                        }
                    }
                    break;
                }
            case R.id.delete:
                {
                    if (solved)
                    {
                        solved = false;
                        screen.setText("");
                    }
                    else if (!text.isEmpty())
                    {
                        text = text.substring(0, text.length() - 1);
                        screen.setText(text);
                        screen.setSelection(screen.getText().length());
                    }
                    break;
                }
            case R.id.openParentheses:
                {
                    if (!text.isEmpty() && isNumber(text.charAt(text.length() - 1) + ""))
                    {
                        screen.setText(text + "×(");
                        screen.setSelection(screen.getText().length());
                    }
                    else
                    {
                        screen.setText(text + "(");
                        screen.setSelection(screen.getText().length());
                    }
                    break;
                }
            case R.id.closeParentheses:
                {
                    if (!text.isEmpty() && text.contains("("))
                    {
                        screen.setText(text + ")");
                        screen.setSelection(text.length());
                    }
                    break;
                }
            case R.id.equal:
                {
                    if (!text.isEmpty())
                    {
                        try
                        {
                            if (text.contains("+") || text.contains("-") || text.contains("×") || text.contains("÷") || text.contains("%"))
                            {
                                Stack<String> stack = new Stack<String>(SY.translate(text));
                                RPN.solve(stack);
                                screen.setText(stack.peak() + "");
                                screen.setSelection(0);
                                operations_records_handler.saveOperation(text, stack.pop());
                                solved = true;
                            }
                        }
                        catch (NotNumericResultException e)
                        {
                            screen.setText("");
                            Snackbar.make(screen, R.string.mistcalc, Snackbar.LENGTH_LONG).setAction(R.string.more_details, new View.OnClickListener()
                                {
                                    public void onClick(View v)
                                    {
                                        showDetails(MainActivity.this, "NULO");
                                    }
                                }).show();
                        }
                        catch (InfinityResultException e)
                        {
                            screen.setText("");
                            Snackbar.make(screen, R.string.mistcalc, Snackbar.LENGTH_LONG).setAction(R.string.more_details, new View.OnClickListener()
                                {
                                    public void onClick(View v)
                                    {
                                        showDetails(MainActivity.this, "INF");
                                    }
                                }).show();
                        }
                        catch (UnbalancedParenthesesException x)
                        {
                            screen.setText("");
                            Snackbar.make(screen, R.string.mistcalc, Snackbar.LENGTH_LONG).setAction(R.string.more_details, new View.OnClickListener()
                                {
                                    public void onClick(View v)
                                    {
                                        showDetails(MainActivity.this, "PIE");
                                    }
                                }).show();
                        }
                        catch (Exception e)
                        {
                            Throwable t = new Throwable(e);
                            StringWriter result = new StringWriter();
                            PrintWriter printer = new PrintWriter(result);
                            while (t != null)
                            {
                                t.printStackTrace(printer);
                                t = t.getCause();
                            }
                            final String exception = result.toString();
                            Snackbar.make(screen, R.string.mistcalc, Snackbar.LENGTH_LONG).setAction(R.string.more_details, new View.OnClickListener()
                                {
                                    public void onClick(View v)
                                    {
                                        showDetails(MainActivity.this, exception);
                                    }
                                }).show();
                        }
                    }
                    break;
                }
            case R.id.toggle_history_drawer:
                {
                    history_drawer.openDrawer(GravityCompat.END);
                    break;
                }
            case R.id.clearHistory_button:
                {
                    operations_records_handler.clearRecords();
                    break;
                }
        }
    }

    @Override
    public boolean onLongClick(View p1)
    {
        switch (p1.getId())
        {
            case R.id.delete:
                {
                    screen.setText("");
                    return true;
                }
            case R.id.screen:
                {
                    if (!screen.getText().toString().isEmpty())
                    {
                        final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                        View edits = getLayoutInflater().inflate(R.layout.screen_menu, null);
                        dialog.setContentView(edits);
                        edits.findViewById(R.id.copy_lay).setOnClickListener(new OnClickListener()
                            {
                                @Override
                                public void onClick(View p1)
                                {
                                    copyToClipboard(null, screen.getText().toString());
                                    dialog.dismiss();
                                    Snackbar.make(screen, R.string.copied, Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        edits.findViewById(R.id.cut_lay).setOnClickListener(new OnClickListener()
                            {
                                @Override
                                public void onClick(View p1)
                                {
                                    solved = false;
                                    copyToClipboard(null, screen.getText().toString());
                                    dialog.dismiss();
                                    screen.setText("");
                                    Snackbar.make(screen, R.string.cutText, Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        dialog.show();
                    }
                    return true;
                }
            default: return false;
        }
    }

    private boolean copyToClipboard(@Nullable String title, @NonNull String description)
    {
        try
        {
            if (description == null)
                return false;
            ClipboardManager cm = (ClipboardManager) MainActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData cd = ClipData.newPlainText(title, description);
            cm.setPrimaryClip(cd);
            return true;
        }
        catch (Exception ignored)
        {}
        return false;
    }

    private void showHistoryItemPopupMenu(final HistoryItem item, View view)
    {
        PopupMenu menu = new PopupMenu(MainActivity.this, view);
        menu.inflate(R.menu.records_item_menu);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(MenuItem p1)
                {
                    switch (p1.getItemId())
                    {
                        case R.id.copy_operation:
                            {
                                copyToClipboard(null, item.getOperation());
                                return true;
                            }
                        case R.id.copy_result:
                            {
                                copyToClipboard(null, item.getResult().replace("=", ""));
                                return true;
                            }
                        case R.id.delete_history_item:
                            {
                                operations_records_handler.deleteOperation(item.getId());
                                return true;
                            }
                        default: return false;
                    }
                }
            });
        menu.setGravity(Gravity.END);
        menu.show();
    }

    private Boolean isNumber(String x)
    {
        for (int i = 0; i <= 9; i++)
        {
            String number = Integer.toString(i);
            if (number.equals(x))
            {
                return true;
            }
        }
        return false;
    }

    private Boolean isOperator(String x)
    {
        return (x.equals("+")) || (x.equals("-")) || (x.equals("×")) || (x.equals("÷")) || (x.equals("%"));
    }

    @Override
    public void onBackPressed()
    {
        final BottomSheetDialog exit = new BottomSheetDialog(this);
        View inflado = getLayoutInflater().inflate(R.layout.exit, null);
        exit.setContentView(inflado);
        exit.setCancelable(false);
        inflado.findViewById(R.id.I_do_want).setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    exit.dismiss();
                    finishAffinity();
                    overridePendingTransition(R.anim.bounce, R.anim.fade_out);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                }
            });
        inflado.findViewById(R.id.I_dont_want).setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    exit.dismiss();
                }
            });
        exit.show();
    }

    private void showDetails(Context context, String type)
    {
        final BottomSheetDialog detError = new BottomSheetDialog(context);
        View inflado = getLayoutInflater().inflate(R.layout.error_dialog, null);
        detError.setContentView(inflado);
        final TextView head = inflado.findViewById(R.id.title);
        final Button send = inflado.findViewById(R.id.SEND);
        final TextView errorT = inflado.findViewById(R.id.errorText);
        send.setVisibility(View.GONE);
        switch (type)
        {
            case "PIE":
                {
                    errorT.setText(R.string.missbalanced);
                    break;
                }
            case "NULO":
                {
                    errorT.setText(R.string.nan_error);
                    break;
                }
            case "INF":
                {
                    errorT.setText(R.string.infinite_error);
                    break;
                }
            default:
                {
                    errorT.setText(R.string.genericE + ": " + type);
                    send.setVisibility(View.VISIBLE);
                    break;
                }
        }
        send.setOnClickListener(new OnClickListener()
            {
                public void onClick(View v)
                {
                    detError.dismiss();
                    startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, errorT.getText().toString()).putExtra(Intent.EXTRA_SUBJECT, head.getText().toString()), getResources().getString(R.string.sendTit)));
                }
            });
        detError.show();
    }
}
