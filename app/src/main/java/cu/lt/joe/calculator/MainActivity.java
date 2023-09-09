package cu.lt.joe.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import cu.lt.joe.calculator.adapters.OperationsHistoryAdapter;
import cu.lt.joe.calculator.db.HistoryDatabaseHandler;
import cu.lt.joe.calculator.models.HistoryItem;
import cu.lt.joe.calculator.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener
{
    private EditText screen;
    private AppCore currentInstance;
    private HistoryDatabaseHandler operations_records_handler;
    private ListView history_lv;
    private DrawerLayout history_drawer;
    private SharedPreferences sharp;
    private SharedPreferences.Editor editor;
    private WebView evaluatorWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        sharp = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        editor = sharp.edit();
        AppCompatDelegate.setDefaultNightMode(sharp.getBoolean("UI_MODE_DARK", false) ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.main);
        currentInstance = (AppCore) getApplication();
        history_drawer = findViewById(R.id.history_drawer);
        history_lv = findViewById(R.id.history_lv);
        history_lv.setDividerHeight(0);
        screen = findViewById(R.id.screen);
        screen.setShowSoftInputOnFocus(false);
        screen.setCursorVisible(false);
        screen.setText(currentInstance.screen_content);
        operations_records_handler = new HistoryDatabaseHandler(this, new HistoryDatabaseHandler.OnDataTransactionListener()
            {
                @Override
                public void onDataChanged()
                {
                    history_lv.setAdapter(new OperationsHistoryAdapter(MainActivity.this, operations_records_handler.getOperationsHistory()));
                    currentInstance.solved = true;
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
                    currentInstance.solved = false;
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
        findViewById(R.id.delete).setOnLongClickListener(this);
        screen.setOnLongClickListener(this);

        evaluatorWebView = new WebView(this);
        evaluatorWebView.getSettings().setJavaScriptEnabled(true);
        evaluatorWebView.setWebChromeClient(new WebChromeClient()
            {
                public boolean onConsoleMessage(ConsoleMessage message)
                {
                    final String result = message.message();
                    if (message.messageLevel().compareTo(ConsoleMessage.MessageLevel.LOG) == 0)
                    {
                        switch (result.toLowerCase())
                        {
                            case "nan":
                                Snackbar.make(screen, R.string.nan_error, Snackbar.LENGTH_SHORT).show();
                                break;
                            case "infinity":
                                Snackbar.make(screen, R.string.infinite_error, Snackbar.LENGTH_SHORT).show();
                                break;
                            default:
                                operations_records_handler.saveOperation(screen.getText().toString(), result);
                                screen.setText(result);
                        }
                    }
                    else
                        Snackbar.make(screen, R.string.mistcalc, Snackbar.LENGTH_SHORT).show();
                    return true;
                }
            });
    }

    @Override
    public void onClick(View p1)
    {
        String text = screen.getText().toString();
        char lastChar = !text.isEmpty() ? text.charAt(text.length() - 1) : ' ';
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
                String number = ((Button)p1).getText().toString();
                if (!text.isEmpty())
                {
                    if (currentInstance.solved)
                    {
                        currentInstance.solved = false;
                        if (lastChar == '(' || lastChar == '.')
                            screen.setText(text + number);
                        else
                            screen.setText(number);
                    }
                    else if (lastChar == ')')
                        screen.setText(text + "×" + number);
                    else
                        screen.setText(text + number);
                }
                else
                    screen.setText(number);
                screen.setSelection(screen.getText().length());
                break;
            case R.id.add:
            case R.id.subtract:
            case R.id.multiply:
            case R.id.divide:
            case R.id.modDiv:
                String operator = ((Button)p1).getText().toString();
                currentInstance.solved = false;
                if (!text.isEmpty())
                {
                    if (Utils.isNumber(lastChar + "") || lastChar == ')')
                        text += operator;
                    else if (lastChar == '.')
                        text += "0" + operator;
                    else if (lastChar == '(' && operator.equals("-"))
                        text += operator;
                    else if (text.endsWith("(-"))
                        text = operator.equals("+") ? text.substring(0, text.length() - 1) : text;
                    else if (Utils.isOperator(lastChar + ""))
                        text = text.substring(0, text.length() - 1) + operator;
                    screen.setText(text);
                    screen.setSelection(screen.getText().length());
                }
                break;
            case R.id.comma:
                if (!text.isEmpty())
                {
                    if (Utils.isNumber(lastChar + ""))
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
            case R.id.delete:
                if (currentInstance.solved)
                {
                    currentInstance.solved = false;
                    screen.setText("");
                }
                else if (!text.isEmpty())
                {
                    text = text.substring(0, text.length() - 1);
                    screen.setText(text);
                    screen.setSelection(text.length());
                }
                break;
            case R.id.openParentheses:
                if (!text.isEmpty() && (Utils.isNumber(lastChar + "") || lastChar == ')'))
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
            case R.id.closeParentheses:
                if (!text.isEmpty() && text.contains("("))
                {
                    screen.setText(text += lastChar == '(' ? "1)" : ")");
                    screen.setSelection(text.length());
                }
                break;
            case R.id.equal:
                if (!text.isEmpty() && (text.contains("+") || text.contains("-") || text.contains("×") || text.contains("÷") || text.contains("%")))
                    evaluatorWebView.evaluateJavascript("console.log(eval(" + text.replace("×", "*").replace("÷", "/") + "))", null);
                break;
            case R.id.toggle_history_drawer:
                history_drawer.openDrawer(GravityCompat.END);
                break;
            case R.id.clearHistory_button:
                operations_records_handler.clearRecords();
                break;
            case R.id.toggle_dayNight_mode:
                editor.putBoolean("UI_MODE_DARK", !(sharp.getBoolean("UI_MODE_DARK", false))).commit();
                currentInstance.screen_content = screen.getText().toString();
                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.blink, R.anim.abc_fade_out);
                break;
        }
    }

    @Override
    public boolean onLongClick(View p1)
    {
        switch (p1.getId())
        {
            case R.id.delete:
                screen.setText(null);
                return true;
            case R.id.screen:
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
                                Utils.copyToClipboard(MainActivity.this, null, screen.getText().toString());
                                dialog.dismiss();
                                Snackbar.make(screen, R.string.copied, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    edits.findViewById(R.id.cut_lay).setOnClickListener(new OnClickListener()
                        {
                            @Override
                            public void onClick(View p1)
                            {
                                currentInstance.solved = false;
                                Utils.copyToClipboard(MainActivity.this, null, screen.getText().toString());
                                dialog.dismiss();
                                screen.setText("");
                                Snackbar.make(screen, R.string.cutText, Snackbar.LENGTH_SHORT).show();
                            }
                        });
                    dialog.show();
                    return true;
                }
            default: return false;
        }
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
                            Utils.copyToClipboard(MainActivity.this, null, item.getOperation());
                            return true;
                        case R.id.copy_result:
                            Utils.copyToClipboard(MainActivity.this, null, item.getResult().replace("=", ""));
                            return true;
                        case R.id.delete_history_item:
                            operations_records_handler.deleteOperation(item.getId());
                            return true;
                        default: return false;
                    }
                }
            });
        menu.setGravity(Gravity.END);
        menu.show();
    }

    @Override
    public void onBackPressed()
    {
        if (history_drawer.isDrawerOpen(GravityCompat.END))
            history_drawer.closeDrawer(GravityCompat.END);
        else
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
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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
    }
}
