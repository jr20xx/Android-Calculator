package cu.lt.ult.jrja.rpn.calc;

import android.annotation.*;
import android.content.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import androidx.appcompat.app.*;
import com.google.android.material.bottomsheet.*;
import com.google.android.material.snackbar.*;
import cu.lt.ult.jrja.rpn.calc.DataStructures.*;
import cu.lt.ult.jrja.rpn.calc.Exceptions.*;
import cu.lt.ult.jrja.rpn.calc.Utils.*;
import java.io.*;

public class MainActivity extends AppCompatActivity
{

    private boolean solved;
    private String lastOp = "";
    private EditText screen;
    private Button residuo, pareA, pareC, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, dividir, multiplicar, restar, sumar, igual, coma;
    private ImageButton borrar;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        
        screen = findViewById(R.id.screen);
        screen.setShowSoftInputOnFocus(false);
        screen.setCursorVisible(false);
        screen.setOnLongClickListener(new OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                if (!lastOp.isEmpty() || !screen.getText().toString().isEmpty())
                {
                    Animation zoomout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.szoom_out);
                    screen.startAnimation(zoomout);
                    final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
                    View edits = getLayoutInflater().inflate(R.layout.screen_menu, null);
                    dialog.setContentView(edits);
                    final LinearLayout copylay = edits.findViewById(R.id.copy_lay);
                    final LinearLayout cutlay = edits.findViewById(R.id.cut_lay);
                    final LinearLayout reportlay = edits.findViewById(R.id.report_lay);

                    if (screen.getText().toString().isEmpty())
                    {
                        copylay.setVisibility(View.GONE);
                        cutlay.setVisibility(View.GONE);
                    }
                    if (lastOp.isEmpty())
                    {
                        reportlay.setVisibility(View.GONE);
                    }

                    copylay.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View p1)
                        {
                            android.content.ClipboardManager clipbrd = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            clipbrd.setText(screen.getText().toString());
                            dialog.dismiss();
                            Snackbar.make(screen, R.string.copied, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                    cutlay.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View p1)
                        {
                            solved = false;
                            android.content.ClipboardManager clipbrd = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            clipbrd.setText(screen.getText().toString());
                            dialog.dismiss();
                            screen.setText("");
                            Snackbar.make(screen, R.string.cutText, Snackbar.LENGTH_SHORT).show();
                        }
                    });
                    reportlay.setOnClickListener(new OnClickListener()
                    {
                        @Override
                        public void onClick(View p1)
                        {
                            solved = false;
                            report();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                return true;
            }
        });

        borrar = findViewById(R.id.borrar);
        num0 = findViewById(R.id.cero);
        num1 = findViewById(R.id.uno);
        num2 = findViewById(R.id.dos);
        num3 = findViewById(R.id.tres);
        num4 = findViewById(R.id.cuatro);
        num5 = findViewById(R.id.cinco);
        num6 = findViewById(R.id.seis);
        num7 = findViewById(R.id.siete);
        num8 = findViewById(R.id.ocho);
        num9 = findViewById(R.id.nueve);
        dividir = findViewById(R.id.entre);
        multiplicar = findViewById(R.id.por);
        restar = findViewById(R.id.menos);
        sumar = findViewById(R.id.sumar);
        igual = findViewById(R.id.igual);
        coma = findViewById(R.id.coma);
        residuo = findViewById(R.id.divModular);
        pareA = findViewById(R.id.parAbre);
        pareC = findViewById(R.id.parCierre);

        num9.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×9");
                }
                else
                {
                    screen.setText(texto + "9");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num8.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×8");
                }
                else
                {
                    screen.setText(texto + "8");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num7.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×7");
                }
                else
                {
                    screen.setText(texto + "7");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num6.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×6");
                }
                else
                {
                    screen.setText(texto + "6");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num5.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×5");
                }
                else
                {
                    screen.setText(texto + "5");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num4.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×4");
                }
                else
                {
                    screen.setText(texto + "4");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num3.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×3");
                }
                else
                {
                    screen.setText(texto + "3");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num2.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×2");
                }
                else
                {
                    screen.setText(texto + "2");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num1.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×1");
                }
                else
                {
                    screen.setText(texto + "1");
                }
                screen.setSelection(screen.getText().length());
            }
        });
        num0.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
                {
                    screen.setText(texto + "×0");
                }
                else
                {
                    screen.setText(texto + "0");
                }
                screen.setSelection(screen.getText().length());
            }
        });

        coma.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty())
                {
                    if (isNumber(texto.charAt(texto.length() - 1) + ""))
                    {
                        String arr[] = texto.replace("(", "( ").replace("+", " + ").replace("-", " - ").replace("×", " × ").replace("÷", " ÷ ").replace("%", " % ").replace(")", " )").replace("n", "-").trim().split(" ");
                        if (!arr[arr.length - 1].contains("."))
                        {
                            screen.setText(screen.getText().toString() + ".");
                            screen.setSelection(screen.getText().length());
                        }
                    }
                }
            }
        });
        sumar.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    screen.setText(texto + "+");
                    screen.setSelection(screen.getText().length());
                }
                else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    texto = texto.substring(0, texto.length() - 1);
                    screen.setText(texto + "+");
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        restar.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    screen.setText(texto + "-");
                    screen.setSelection(screen.getText().length());
                }
                else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    texto = texto.substring(0, texto.length() - 1);
                    screen.setText(texto + "-");
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        multiplicar.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    screen.setText(texto + "×");
                    screen.setSelection(screen.getText().length());
                }
                else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    texto = texto.substring(0, texto.length() - 1);
                    screen.setText(texto + "×");
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        dividir.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    screen.setText(texto + "÷");
                    screen.setSelection(screen.getText().length());
                }
                else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    texto = texto.substring(0, texto.length() - 1);
                    screen.setText(texto + "÷");
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        residuo.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    screen.setText(texto + "%");
                    screen.setSelection(screen.getText().length());
                }
                else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
                {
                    texto = texto.substring(0, texto.length() - 1);
                    screen.setText(texto + "%");
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        pareA.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (!texto.isEmpty() && isNumber(texto.charAt(texto.length() - 1) + ""))
                {
                    screen.setText(texto + "×(");
                    screen.setSelection(screen.getText().length());
                }
                else
                {
                    screen.setText(texto + "(");
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        pareC.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if (!screen.getText().toString().isEmpty())
                {
                    screen.setText(screen.getText().toString() + ")");
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        borrar.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String texto = screen.getText().toString();
                if (solved)
                {
                    solved = false;
                    screen.setText("");
                }
                else if (!texto.isEmpty())
                {
                    texto = texto.substring(0, texto.length() - 1);
                    screen.setText(texto);
                    screen.setSelection(screen.getText().length());
                }
            }
        });
        borrar.setOnLongClickListener(new OnLongClickListener()
        {
            public boolean onLongClick(View v)
            {
                while (!screen.getText().toString().isEmpty())
                {
                    screen.setText(screen.getText().toString().substring(0, screen.getText().toString().length() - 1));
                    screen.setSelection(screen.getText().length());
                }
                return true;
            }
        });
        igual.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                String text = screen.getText().toString();
                if (!text.isEmpty())
                {
                    try
                    {
                        String Text = screen.getText().toString();
                        if (Text.contains("+") || Text.contains("-") || Text.contains("×") || Text.contains("÷") || Text.contains("%") || Text.contains("^"))
                        {
                            Text = Text.replace("×", "*").replace("÷", "/");
                            Cola<String> sy = SY.translate(Text);
                            Pila<String> stack = new Pila<String>();
                            stack.parsePila(sy);
                            RPN.solve(stack);
                            screen.setText(stack.peak() + "");
                            screen.setSelection(0);
                            lastOp = Text + "=" + stack.pop();
                            solved = true;
                        }
                    }
                    catch (ParéntesisSinEmparejar x)
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
                        while(t != null)
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
            }
        });

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
            {
            }
        });

        screen.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
            {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
            {
                String texto = screen.getText().toString();
                if (solved && !texto.isEmpty())
                {
                    solved = false;
                    if((texto.charAt(texto.length() - 1) + "").equals("(") || (texto.charAt(texto.length() - 1) + "").equals("."))
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
                else if (texto.contains("(+") || texto.contains("(×") || texto.contains("(÷") || texto.contains("(%") || texto.contains("(^"))
                {
                    screen.setText(texto.replace("(+", "(").replace("(×", "(").replace("(÷", "(").replace("(%", "(").replace("(^", "("));
                }
                else if (texto.contains("()"))
                {
                    screen.setText(texto.replace("()", "(1)"));
                }
                else if (texto.equals("null") || texto.equals("NaN"))
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
                else if (texto.equals("Infinity"))
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
            }

            @Override
            public void afterTextChanged(Editable p1)
            {
            }
        });

        screen.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    return false;
                return true;
            }
        });

    }

    private Boolean isNumber(String x)
    {
        for (int i = 0; i <= 9; i++)
        {
            String cosanga = Integer.toString(i);
            if (cosanga.equals(x))
            {
                return true;
            }
        }
        return false;
    }

    private Boolean isOperator(String x)
    {
        return (x.equals("+")) || (x.equals("-")) || (x.equals("×")) || (x.equals("÷")) || (x.equals("%")) || (x.equals("^"));
    }

    @Override
    public void onBackPressed()
    {
        final BottomSheetDialog exit = new BottomSheetDialog(this);
        View inflado = getLayoutInflater().inflate(R.layout.exit, null);
        exit.setContentView(inflado);
        exit.setCancelable(false);
        final Button ok = inflado.findViewById(R.id.I_do_want);
        final Button notOK = inflado.findViewById(R.id.I_dont_want);
        ok.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                exit.dismiss();
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
            }
        });
        notOK.setOnClickListener(new OnClickListener()
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

        switch (type)
        {
            case "PIE":
            {
                errorT.setText(R.string.missbalanced);
                send.setVisibility(View.GONE);
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

    private void report()
    {
        final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
        View report = getLayoutInflater().inflate(R.layout.report_bad_result, null);
        dialog.setContentView(report);
        dialog.setCancelable(false);
        final Button ok = report.findViewById(R.id.I_do);
        final Button notOk = report.findViewById(R.id.I_dont);
        ok.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(Intent.createChooser(new Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, lastOp), getResources().getString(R.string.shareOp)));
                dialog.dismiss();
            }
        });
        notOk.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View p1)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
