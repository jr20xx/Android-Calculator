package cu.lt.joe.calculator.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Utils
{
    public static boolean isOperator(String x)
    {
        return (x.equals("+")) || (x.equals("-")) || (x.equals("×")) || (x.equals("÷")) || (x.equals("%"));
    }
    public static boolean isNumber(String x)
    {
        return x.matches("^[+-]?\\d+(?:\\.\\d*(?:[eE][+-]?\\d+)?)?$");
    }
    public static boolean copyToClipboard(@NonNull Context context, @Nullable String title, @NonNull String description)
    {
        if (description != null)
        {
            try
            {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData cd = ClipData.newPlainText(title, description);
                cm.setPrimaryClip(cd);
                return true;
            }
            catch (Exception ignored)
            {}
        }
        return false;
    }
}
