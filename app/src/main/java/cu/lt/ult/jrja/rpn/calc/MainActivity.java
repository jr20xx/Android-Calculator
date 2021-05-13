package cu.lt.ult.jrja.rpn.calc;

import android.os.*;
import android.support.v7.app.*;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.view.View.*;
import android.support.design.widget.*;
import android.content.*;
import android.view.inputmethod.*;
import android.text.*;
import android.support.v4.widget.*;
import android.view.animation.*;
import android.app.*;
import android.animation.*;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener
{
	private GestureDetector gestureDetector = new GestureDetector(this);
	private String lastOp="";
	private EditText screen;
	private Button potencia, residuo, pareA, pareC, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, borrar, dividir, multiplicar, restar, sumar, igual, coma;
	
	@Override
	public boolean onDown(MotionEvent p1)
	{
		return false;
	}

	@Override
	public void onShowPress(MotionEvent p1)
	{}

	@Override
	public boolean onSingleTapUp(MotionEvent p1)
	{
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent p1, MotionEvent p2, float p3, float p4)
	{
		return false;
	}

	@Override
	public void onLongPress(MotionEvent p1)
	{}

	@Override
	public boolean onFling(MotionEvent downEvent, MotionEvent moveEvent, float speedX, float speedY)
	{
		float diffY = moveEvent.getY() - downEvent.getY();
		float diffX = moveEvent.getX() - downEvent.getX();

		if (Math.abs(diffY) > Math.abs(diffX))
		{
			if ((Math.abs(diffY) > 100) && (Math.abs(speedY) > 100))
			{
				if (diffY < 0)
				{
					//swiped up
					if (!this.lastOp.equals(""))
						this.report();
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main);
		getSupportActionBar().hide();

		screen = findViewById(R.id.screen);
		screen.setShowSoftInputOnFocus(false);
		screen.setCursorVisible(false);
		screen.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "Hack.ttf"));
		screen.setOnLongClickListener(new OnLongClickListener(){
				public boolean onLongClick(View v)
				{
					if (!screen.getText().toString().isEmpty())
					{
						Animation zoomout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.szoom_out);
						android.content.ClipboardManager clipbrd = (android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
						screen.startAnimation(zoomout);
						clipbrd.setText(screen.getText().toString());
						Snackbar snack = Snackbar.make(screen, R.string.copied, Snackbar.LENGTH_SHORT);				
						snack.show();
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
		potencia = findViewById(R.id.potencia);
		residuo = findViewById(R.id.divModular);
		pareA = findViewById(R.id.parAbre);
		pareC = findViewById(R.id.parCierre);

		borrar.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num0.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num1.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num2.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num3.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num4.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num5.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num6.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num7.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num8.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		num9.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		sumar.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		multiplicar.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		residuo.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		dividir.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		residuo.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		pareA.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		pareC.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		igual.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		coma.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));
		potencia.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(), "DejaVu.ttf"));

		num9.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*9");
					else
						screen.setText(texto + "9");
					screen.setSelection(screen.getText().length());
				}
			});
		num8.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*8");
					else
						screen.setText(texto + "8");
					screen.setSelection(screen.getText().length());
				}
			});
		num7.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*7");
					else
						screen.setText(texto + "7");
					screen.setSelection(screen.getText().length());
				}
			});
		num6.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*6");
					else
						screen.setText(texto + "6");
					screen.setSelection(screen.getText().length());
				}
			});
		num5.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*5");
					else
						screen.setText(texto + "5");
					screen.setSelection(screen.getText().length());
				}
			});
		num4.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*4");
					else
						screen.setText(texto + "4");
					screen.setSelection(screen.getText().length());
				}
			});
		num3.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*3");
					else
						screen.setText(texto + "3");
					screen.setSelection(screen.getText().length());
				}
			});
		num2.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*2");
					else
						screen.setText(texto + "2");
					screen.setSelection(screen.getText().length());
				}
			});
		num1.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*1");
					else
						screen.setText(texto + "1");
					screen.setSelection(screen.getText().length());
				}
			});
		num0.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*0");
					else
						screen.setText(texto + "0");
					screen.setSelection(screen.getText().length());
				}
			});

		coma.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();
					if (!texto.isEmpty())
					{
						if (isNumber(texto.charAt(texto.length() - 1) + ""))
						{
							String arr[] = texto.replace("(", "( ").replace("+", " + ").replace("-", " - ").replace("*", " * ").replace("/", " / ").replace("%", " % ").replace("^", " ^ ").replace(")", " )").replace("n", "-").trim().split(" ");
							if (!arr[arr.length - 1].contains("."))
							{
								screen.setText(screen.getText().append("."));
							}
						}
					}
				}
			});
		sumar.setOnClickListener(new OnClickListener(){
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
		restar.setOnClickListener(new OnClickListener(){
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
		multiplicar.setOnClickListener(new OnClickListener(){
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
		dividir.setOnClickListener(new OnClickListener(){
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
		potencia.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();
					if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						screen.setText(texto + "^");
						screen.setSelection(screen.getText().length());
					}
					else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto + "^");
						screen.setSelection(screen.getText().length());
					}
				}
			});
		residuo.setOnClickListener(new OnClickListener(){
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
		pareA.setOnClickListener(new OnClickListener(){
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
		pareC.setOnClickListener(new OnClickListener(){
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
					if (!texto.isEmpty())
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
					String texto = screen.getText().toString();
					while (!texto.isEmpty())
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto);
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
							if (Text.contains("×"))
								Text = Text.replace("×", "*");
							if (Text.contains("÷"))
								Text = Text.replace("÷", "/");
							SY sy = new SY(Text);
							Cola<String> x = sy.translate();
							Pila<String> stack = new Pila<String>();
							stack.parsePila(x);
							RPN rpn = new RPN(stack);
							rpn.solve();
							screen.setText(stack.peak() + "");
							screen.setSelection(0);
							lastOp = Text + "=" + stack.pop();
						}
						catch (ParéntesisSinEmparejar x)
						{
							Snackbar snack = Snackbar.make(screen, R.string.mistcalc , Snackbar.LENGTH_LONG);
							snack.setAction(R.string.more_details, new View.OnClickListener()
								{
									public void onClick(View v)
									{
										showDetails(MainActivity.this, "PIE");
									}
								});
							snack.show();
						}
						catch (Exception e)
						{
							final String exception = e.getStackTrace().toString();
							Snackbar snack = Snackbar.make(screen, R.string.mistcalc , Snackbar.LENGTH_LONG);
							snack.setAction(R.string.more_details, new View.OnClickListener()
								{
									public void onClick(View v)
									{
										showDetails(MainActivity.this, exception);
									}
								});
							snack.show();
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
				{}			
			});

		screen.addTextChangedListener(new TextWatcher(){
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					String texto = screen.getText().toString();
					if (texto.contains(")("))
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
						Snackbar snack = Snackbar.make(screen, R.string.mistcalc , Snackbar.LENGTH_LONG);
						snack.setAction(R.string.more_details, new View.OnClickListener()
							{
								public void onClick(View v)
								{
									showDetails(MainActivity.this, "NULO");
								}
							});
						snack.show();
					}
					else if (texto.equals("Infinity"))
					{
						screen.setText("");
						Snackbar snack = Snackbar.make(screen, R.string.mistcalc , Snackbar.LENGTH_LONG);
						snack.setAction(R.string.more_details, new View.OnClickListener()
							{
								public void onClick(View v)
								{
									showDetails(MainActivity.this, "INF");
								}
							});
						snack.show();
					}
				}

				@Override
				public void afterTextChanged(Editable p1)
				{}
			});

    }

	private Boolean isNumber(String x)
	{
		for (int i = 0; i <= 9; i++)
		{
			String cosanga = Integer.toString(i);
			if (cosanga.equals(x)) return true;
		}
		return false;
	}

	private Boolean isOperator(String x)
	{
		return (x.equals("+")) || (x.equals("-")) || (x.equals("*")) || (x.equals("/")) || (x.equals("%")) || (x.equals("^"));
	}

	@Override
	public void onBackPressed()
	{
		final android.support.design.widget.BottomSheetDialog exit = new android.support.design.widget.BottomSheetDialog(this);
		View inflado = getLayoutInflater().inflate(R.layout.exit, null);
		exit.setContentView(inflado);
		exit.setCancelable(false);
		final Button ok = inflado.findViewById(R.id.I_do_want);
		final Button notOK = inflado.findViewById(R.id.I_dont_want);
		ok.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					exit.dismiss();
					finishAffinity();
					int pid = android.os.Process.myPid();
					android.os.Process.killProcess(pid);
					Intent intento = new Intent(Intent.ACTION_MAIN);
					intento.addCategory(Intent.CATEGORY_HOME);
					startActivity(intento);
				}
			});
		notOK.setOnClickListener(new OnClickListener(){
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
					Intent share = new Intent(Intent.ACTION_SEND);
					share.setType("text/plain");
					share.putExtra(Intent.EXTRA_TEXT, errorT.getText().toString());
					share.putExtra(Intent.EXTRA_SUBJECT, head.getText().toString());				
					startActivity(Intent.createChooser(share, getResources().getString(R.string.sendTit)));
				}
			});
		detError.show();
	}
	private void report()
	{
		final BottomSheetDialog dialog = new BottomSheetDialog(MainActivity.this);
		View report = getLayoutInflater().inflate(R.layout.report_bad_result, null);
		dialog.setContentView(report);
		final Button ok = report.findViewById(R.id.I_do);
		final Button notOk = report.findViewById(R.id.I_dont);
		ok.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					Intent share = new Intent(Intent.ACTION_SEND);
					share.setType("text/plain");
					share.putExtra(Intent.EXTRA_TEXT, lastOp);
					//share.putExtra(Intent.EXTRA_SUBJECT, header);				
					startActivity(Intent.createChooser(share, getResources().getString(R.string.shareOp)));
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

	/*WIP
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return super.onKeyDown(keyCode, event);
	}*/
	
}
