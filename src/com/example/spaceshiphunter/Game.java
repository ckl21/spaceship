package com.example.spaceshiphunter;



import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;

public class Game extends Activity {
   
   ScrollableImageView scrollImageView;
   private GestureDetector myDetector;
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      WindowManager w = getWindowManager();
      Display d = w.getDefaultDisplay();
      
      Point point = new Point();
      d.getSize(point);
      scrollImageView = new ScrollableImageView(this,BitmapFactory.decodeResource(getResources(), R.drawable.space_bg), point.x, point.y, null);
      setContentView(scrollImageView);
      
      myDetector = new GestureDetector(this, gestureListener);
   }
   
   OnGestureListener gestureListener = new OnGestureListener() {
	
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		scrollImageView.handleScroll(distanceX, distanceY);
         return true;
	}
	
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
};
   
   public boolean onTouchEvent(MotionEvent event) {
      return myDetector.onTouchEvent(event);
   }
   
   ////
   ////

   class ScrollableImageView extends View {
      
      int scrollRate = 40;
      
      int scrollX = 0;
      
      int scrollY = 0;
      
      boolean scrollHorizontalEnabled = true;
      
      boolean scrollVerticalEnabled = true;
      
      ////
      
      Bitmap image;
      
      Bitmap bufferImage;
      
      int maxWidth;
      
      int maxHeight;
      
      int pictureWidth;
      
      int pictureHeight;
      
      ////
      
      Paint paint;
      
      
      ////
      ////

      public ScrollableImageView(Context context, Bitmap image, int width,
            int height, Paint paint) {
         super(context);
         this.image = image;
         this.paint = paint;
         
         bufferImage = Bitmap.createBitmap(image);
         
         calculateSize(width, height);
      }
      
      public ScrollableImageView(Context context, Bitmap image,
            int width, int height, Paint paint,
            boolean scrollHorizontal, boolean scrollVertical) {
         super(context);
         this.image = image;
         this.paint = paint;
         this.scrollHorizontalEnabled = scrollHorizontal;
         this.scrollVerticalEnabled = scrollVertical;
         
         bufferImage = Bitmap.createBitmap(image);
         
         calculateSize(width, height);
      }
      
      protected void calculateSize(int width, int height) {
         
         //picture size
         pictureWidth = image.getWidth();
         pictureHeight = image.getHeight();
         
         //window size
         maxWidth = Math.min(pictureWidth, width);
         maxHeight = Math.min(pictureHeight, height);
         
         //layout size
         setLayoutParams(new LayoutParams(pictureWidth, pictureHeight));
      }
      
 
      
      @Override
      protected void onDraw(Canvas canvas) {
         canvas.drawBitmap(bufferImage, 0, 0, paint);
      }

      public void handleScroll(float distX, float distY) {
         
         int maxScrollX = Math.max(pictureWidth - maxWidth, 0);
         int maxScrollY = Math.max(pictureHeight - maxHeight, 0);
         
         //X-Axis
         if(scrollHorizontalEnabled){
            if (distX > 6.0) {
               if (scrollX < maxScrollX - scrollRate) {
                  scrollX += scrollRate;
               }
               else {
                  scrollX = maxScrollX;
               }
            } else if (distX < -6.0) {
               if (scrollX >= scrollRate) {
                  scrollX -= scrollRate;
               }
               else {
                  scrollX = 0;
               }
            }
         }

         //Y-AXIS
         if(scrollVerticalEnabled){
            if (distY > 6.0) {
               if (scrollY < maxScrollY - scrollRate) {
                  scrollY += scrollRate;
               }
               else {
                  
               }
            } else if (distY < -6.0) {
               if (scrollY >= scrollRate) {
                  scrollY -= scrollRate;
               }
               else {
                  scrollY = 0;
               }
            }
         }
         
         //Swap image
         if ((scrollX <= maxWidth) && (scrollY <= maxHeight)) {
            swapImage();
            invalidate();
         }
      }
      
      protected void swapImage() {
         bufferImage = Bitmap.createBitmap(image, scrollX, scrollY,
               maxWidth, maxHeight);
      }
     
   }
}
