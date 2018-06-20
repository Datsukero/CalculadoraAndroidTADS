package com.android.claudinei.calculadora;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    String currentString="0",previusString=null;
    boolean isTempStringShown=false;
    int currentopperand=0;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.result);

        int numberButtons[]={R.id.b0,R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7,R.id.b8,R.id.b9};
        NumberButtonClickListener numberClickListener=new NumberButtonClickListener();

        for(int id:numberButtons) {
            View v=findViewById(id);
            v.setOnClickListener(numberClickListener);
        }

        int opperandButtons[]={R.id.bMais,R.id.bMenos,R.id.bDiv,R.id.bMulti,R.id.buttonDecimal,R.id.bClear,R.id.bIgual};
        OpperandButtonClickListener oppClickListener=new OpperandButtonClickListener();

        for(int id:opperandButtons) {
            View v=findViewById(id);
            v.setOnClickListener((View.OnClickListener) oppClickListener);
        }
        setCurrentString("0");
    }

    void setCurrentString(String s) {
        currentString=s;
        textView.setText(s);
    }

    class NumberButtonClickListener implements View.OnClickListener  {
        @Override public void onClick(View v) {
            if(isTempStringShown) {
                previusString=currentString;
                currentString="0";
                isTempStringShown=false;
            }
            String text=(String)((Button)v).getText();
            if(currentString.equals("0"))setCurrentString(text);
            else setCurrentString(currentString+text);
        }
    }

    class OpperandButtonClickListener implements View.OnClickListener  {
        @Override public void onClick(View v) {
            int id=v.getId();
            if(id==R.id.bClear) {
                isTempStringShown=false;
                setCurrentString("0");
                previusString=null;
            }
            if(id==R.id.buttonDecimal)if(!currentString.contains("."))setCurrentString(currentString+".");
            if(id==R.id.bMais||id==R.id.bMenos||id==R.id.bMulti||id==R.id.bDiv) {
                currentopperand=id;
                previusString=currentString;
                isTempStringShown=true;
            }
            if(id==R.id.bIgual) {
                double curr=Double.parseDouble(currentString);
                double result=0;
                if(previusString!=null) {
                    double prev=Double.parseDouble(previusString);
                    switch(currentopperand) {
                        case R.id.bMais: result=prev+curr; break;
                        case R.id.bMenos: result=prev-curr; break;
                        case R.id.bMulti: result=prev*curr; break;
                        case R.id.bDiv: result=prev/curr; break;
                    }
                }
                setCurrentString(Double.toString(result));
            }
        }
    }
}
