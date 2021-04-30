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

public class MainActivity extends AppCompatActivity 
{

	private HorizontalScrollView scroll;
	private Boolean comaAllowed;
	private TextView screen;
	private Button potencia, residuo, pareA, pareC, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, borrar, dividir, multiplicar, restar, sumar, igual, coma;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.main);
		getSupportActionBar().hide();

		comaAllowed = true;

		screen = findViewById(R.id.screen);
		TextViewCompat.setAutoSizeTextTypeWithDefaults(screen, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
		android.graphics.drawable.GradientDrawable screen_Style = new android.graphics.drawable.GradientDrawable();
		screen_Style.setCornerRadius(20);
		screen_Style.setColor(Color.parseColor("#434343"));
		screen.setBackground(screen_Style);
		screen.setOnLongClickListener(new OnLongClickListener(){
				public boolean onLongClick(View v)
				{
					android.content.ClipboardManager clipbrd = (android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
					clipbrd.setText(screen.getText().toString());
					Snackbar snack = Snackbar.make(screen, "Texto copiado al portapapeles", Snackbar.LENGTH_SHORT);				
					snack.show();
					return true;
				}
			});
			
		scroll = findViewById(R.id.scroll);

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

		num9.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();					
					if (!texto.isEmpty() && texto.charAt(texto.length() - 1) == ')')
						screen.setText(texto + "*9");
					else
						screen.setText(texto + "9");
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
				}
			});

		coma.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					if (comaAllowed)
					{
						String texto = screen.getText().toString();
						if (!texto.isEmpty() && isNumber(texto.charAt(texto.length() - 1) + ""))
						{
							screen.setText(screen.getText().toString() + ".");
							comaAllowed = false;
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
						comaAllowed = true;
					}
					else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto + "+");
						comaAllowed = true;
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
						comaAllowed = true;
					}
					else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto + "-");
						comaAllowed = true;
					}
				}
			});
		multiplicar.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();
					if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						screen.setText(texto + "*");
						comaAllowed = true;
					}
					else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto + "*");
						comaAllowed = true;
					}
				}
			});
		dividir.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();
					if (!texto.isEmpty() && !isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						screen.setText(texto + "/");
						comaAllowed = true;
					}
					else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto + "/");
						comaAllowed = true;
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
						comaAllowed = true;
					}
					else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto + "^");
						comaAllowed = true;
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
						comaAllowed = true;
					}
					else if (!texto.isEmpty() && isOperator(texto.charAt(texto.length() - 1) + ""))
					{
						texto = texto.substring(0, texto.length() - 1);
						screen.setText(texto + "%");
						comaAllowed = true;
					}
				}
			});
		pareA.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					String texto = screen.getText().toString();
					if (!texto.isEmpty() && isNumber(texto.charAt(texto.length() - 1) + ""))
					{
						screen.setText(texto + "*(");
						comaAllowed = true;
					}
					else
					{
						screen.setText(texto + "(");
						comaAllowed = true;
					}
				}
			});
		pareC.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(!screen.getText().toString().isEmpty())
					screen.setText(screen.getText().toString()+")");
			}
		});
		borrar.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String texto = screen.getText().toString();
				if(!texto.isEmpty())
				{
					texto = texto.substring(0, texto.length() - 1);
					screen.setText(texto);
				}
			}
		});
		borrar.setOnLongClickListener(new OnLongClickListener()
		{
			public boolean onLongClick(View v)
			{
				String texto = screen.getText().toString();
				while(!texto.isEmpty())
				{
					texto = texto.substring(0, texto.length() - 1);
					screen.setText(texto);
				}
				return true;
			}
		});
		igual.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String text = screen.getText().toString();
				if(!text.isEmpty())
				{
					try
					{
						ShuntingYard sy = new ShuntingYard(screen.getText().toString());
						Cola<String> x = sy.shuntingYard();
						Pila<String> stack = new Pila<String>();
						stack.parsePila(x);
						RPN rpn = new RPN(stack);
						rpn.solve();
						screen.setText(stack.pop()+"");
					}
					catch(Exception e)
					{
						Snackbar snack = Snackbar.make(screen, e.toString() , Snackbar.LENGTH_LONG);
						snack.show();
					}
				}
			}
		});
		

		screen.addTextChangedListener(new TextWatcher(){
				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					String texto = screen.getText().toString();
					if(texto.contains(")(")){
						screen.setText(texto.replace(")(",")*("));
					}
					if(texto.contains("()")){
						screen.setText(texto.replace("()","(1)"));
					}
					if(texto.equals("null"))
					{
						screen.setText("");
						Snackbar snack = Snackbar.make(screen,"Ha ocurrido un error al calcular el resultado",Snackbar.LENGTH_LONG);
						snack.show();
					}
					scroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
				}

				@Override
				public void afterTextChanged(Editable p1)
				{}
			});

    }

	public Boolean isNumber(String x)
	{
		for (int i = 0; i <= 9; i++)
		{
			String cosanga = Integer.toString(i);
			if (cosanga.equals(x)) return true;
		}
		return false;
	}

	public Boolean isOperator(String x)
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
		android.graphics.drawable.GradientDrawable background = new android.graphics.drawable.GradientDrawable();
		background.setCornerRadius(7); //#A82900
		background.setColor(Color.parseColor("#813200"));
		ok.setBackground(background);
		ok.setOnClickListener(new OnClickListener(){
				public void onClick(View v)
				{
					exit.dismiss();
					finishAffinity();
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

}
