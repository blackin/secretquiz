package ro.blackin.secretquiz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import ro.blackin.secretquiz.models.Quiz;
import ro.blackin.secretquiz.providers.HardCodedQuizProvider;
import ro.blackin.secretquiz.providers.QuizProvider;
import ro.blackin.secretquiz.skeleton.BaseActivity;
import ro.blackin.secretquiz.utils.Statics;

public class MainActivity extends BaseActivity
{
    //VIEWS
    ViewFlipper vfMainViewFlipper;


    //CONSTANTS
    private final int SPLASH_FRAGMENT_POSITION = 0;
    private final int QUIZ_MAIN_FRAGMENT_POSITION = 1;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            getSupportActionBar().hide();
        }
        catch (Exception ex)
        {
            //There is no supportActionBar :)
            ex.printStackTrace();
        }

        //Add splash fragment
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.vfMainViewFlipper, new SplashScreenFragment())
//                .commit();

        //Incarca quiz-ul din provider
        HardCodedQuizProvider quizProvider = new HardCodedQuizProvider();
        Statics.currentQuiz = quizProvider.getQuiz();

        initUI();

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vfMainViewFlipper.showNext();
            }
        }, 2000);



    }

    private void initUI()
    {
        vfMainViewFlipper = ( ViewFlipper ) findViewById(R.id.vfMainViewFlipper);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The splash fragment
     */
    public static class SplashScreenFragment extends Fragment {

        public SplashScreenFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_splash, container, false);
            return rootView;
        }
    }

    /**
     * Start Quiz Fragment
     */

    public static class QuizMainFragment extends Fragment
    {
        Button btnStartQuiz;

        public QuizMainFragment(){
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_quizmain, container, false);
            initUI( rootView );
            return rootView;
        }

        private void initUI( View rootView )
        {
            //TODO: FindViewById-type code
            btnStartQuiz = ( Button ) rootView.findViewById(R.id.btnStartQuiz);
            btnStartQuiz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent();
                    intent.setClass( getActivity() , TakeQuizActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.fade_in, R.anim.abc_fade_out);
                }
            });
        }
    }

}
