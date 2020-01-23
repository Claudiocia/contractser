package au.com.contractser.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {

    Paint paint = new Paint();
    ArrayList<Line> lines = new ArrayList<Line>();
    Line currentLine;
    Bitmap mBitmap;


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(3f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        print(canvas);
    }

    /**
     * Desenha no canvas as linhas
     * @param canvas
     */
    private void print(final Canvas canvas){
        // Deesenha todas as linha já escritas
        for(final Line l : lines){
            int max = l.pointers.size()-1;
            for(int pt =0; pt < max; pt++ ) {
                Pointer pA = l.pointers.get(pt);
                Pointer pB = l.pointers.get(pt+1);
                canvas.drawLine(pA._x, pA._y, pB._x, pB._y, paint);
            }
        }
        // Desenha a linha corrente!
        if(currentLine != null){
            int max = currentLine.pointers.size()-1;
            for(int pt =0; pt < max; pt++ ) {
                Pointer pA = currentLine.pointers.get(pt);
                Pointer pB = currentLine.pointers.get(pt+1);
                canvas.drawLine(pA._x, pA._y, pB._x, pB._y, paint);
            }
        }
    }

    public Bitmap generateBitmap() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(mBitmap);
        print(c);
        return mBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Inicia uma nova linha
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            currentLine = new Line();
            currentLine.addPointer(new Pointer(event.getX(), event.getY()));
            invalidate();
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_MOVE) { // adiciona os pontos a linha
            currentLine.addPointer(new Pointer(event.getX(), event.getY()));
            invalidate();
            return true;
        }else if(event.getAction() == MotionEvent.ACTION_UP ){ // finaliza a linha
            lines.add(currentLine);
            currentLine = null;
            invalidate();
            return true;
        }else{
            return false;
        }
    }


    /**
     * Representa um ponto
     */
    class Pointer{
        float _x, _y;
        public Pointer(float _x, float _y){
            this._x = _x;
            this._y = _y;
        }
    }

    /**
     * Representa uma linha (formado por uma lista de pontos)
     */
    class Line {
        List<Pointer> pointers = new ArrayList<>();
        public void addPointer(final Pointer p){
            pointers.add(p);
        }
    }
}
