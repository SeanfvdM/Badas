package com.badas.badassolution;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.badas.badasstyle.FontDownloader.Font;
import com.badas.badasstyle.FontDownloader.FontDialogFragment;
import com.badas.badasstyle.FontDownloader.FontDownloader;
import com.badas.profilemanager.ManagerFragment;
import com.badas.profilemanager.Profile;
import com.badas.studentactivity.Student;
import com.badas.studentactivity.StudentActivityFragment;
import com.badas.studentresults.ResultFragment;
import com.badas.studentresults.StudentResult;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.menu));
        setSupportActionBar(toolbar);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        DynamicMenu.getInstance().add(
                new DynamicMenu.MenuItem(R.id.nav_home)
                        .setTitle(getString(R.string.menu_home))
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_home)));

        DynamicMenu.getInstance().add(new DynamicMenu.SubMenu(0)
                .setTitle(getString(R.string.module_name_sr) + " - " + getString(R.string.module_developer_sr))
                .add(new DynamicMenu.MenuItem(R.id.nav_result)
                        .setTitle(getString(R.string.menu_result))
                        .setGroup(1)
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_result)))
                .add(new DynamicMenu.MenuItem(R.id.nav_student_result)
                        .setTitle(getString(R.string.menu_student_result))
                        .setGroup(1)
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_result_filter))));

        DynamicMenu.getInstance().add(new DynamicMenu.SubMenu(1)
                .setTitle(getString(R.string.module_name_pm) + " - " + getString(R.string.module_developer_pm))
                .add(new DynamicMenu.MenuItem(R.id.nav_profile_manager)
                        .setTitle(getString(R.string.menu_profile_manager))
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_people))));

        DynamicMenu.getInstance().add(new DynamicMenu.SubMenu(2)
                .setTitle(getString(R.string.module_name_sa) + " - " + getString(R.string.module_developer_sa))
                .add(new DynamicMenu.MenuItem(R.id.nav_student_activity)
                        .setTitle(getString(R.string.menu_student_activity))
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_work))));

        DynamicMenu.getInstance().add(new DynamicMenu.SubMenu(3)
                .setTitle(getString(R.string.module_name_gm) + " - " + getString(R.string.module_developer_gm))
                .add(new DynamicMenu.MenuItem(R.id.nav_game_manager)
                        .setTitle(getString(R.string.menu_game_manager))
                        .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_gamepad_24))));

        DynamicMenu.getInstance().attach(navigationView);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                DynamicMenu.getInstance().getIDs())
                .setOpenableLayout((Openable) findViewById(R.id.drawer_layout))
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_result || destination.getId() == R.id.nav_student_result) {
                    if (ResultFragment.getCurrentResultsData().size() < 1) {
                        ArrayList<StudentResult> resultsData = new ArrayList<>();
                        Calendar calendar = Calendar.getInstance();
                        ArrayList<StudentResult.ResultData> data;
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 50; j++) {
                                data = new ArrayList<>();
                                calendar.add(Calendar.DAY_OF_YEAR, j);
                                data.add(new StudentResult.ResultData("Date:", new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.getTime())));
                                for (int k = 1; k < i + 1; k++)
                                    data.add(new StudentResult.ResultData("Text " + k + ":", "Value " + k));
                                resultsData.add(
                                        new StudentResult()
                                                .setStudent("Student " + i)
                                                .setHeader("Item: " + j)
                                                .setResultsData(data)
                                );
                            }
                        }

                        ResultFragment.initDataSet(resultsData);
                    }
                } else if (destination.getId() == R.id.nav_student_activity) {
                    ArrayList<Student> students = new ArrayList<>();
                    students.add(new Student("Sean", new Date(), (long) 500000, "test", "test"));
                    students.add(new Student("Aharon", new Date(), (long) 1500000, "test2", "test"));
                    students.add(new Student("Daniel", new Date(), (long) 10000, "test", "test3"));
                    students.add(new Student("Brandon", new Date(), (long) 506300, "test4", "test4"));
                    students.add(new Student("Avremi", new Date(), (long) 1000, "test5", "test5"));

                    StudentActivityFragment.initDataSet(students);
                } else if (destination.getId() == R.id.nav_profile_manager) {
                    ArrayList<Profile> userList = new ArrayList<>();

                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Aharon", "Email:\talexkangy@gmail.com \nPhone Number:\t0749597678"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Sean", "Email:\tSean@gmail.com \nPhone Number:\t0825412578"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Daniel", "Email:\tDaniel@gmail.com \nPhone Number:\t0720215128"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Brandon", "Email:\tBrandon@gmail.com \nPhone Number:\t0832514325"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Avremi", "Email:\tAvremi@gmail.com \nPhone Number:\t0821986254"));

                    ManagerFragment.initDataSet(userList);
                }
            }
        });

        if (false) {
            String apiKey = "AIzaSyBz-ZqLY0jXSlGwq1E3lN9JK_S_hI1ThMg";
            new FontDownloader()
                    .setJsonLabels(FontDownloader.FontJsonLabels.GOOGLE_FONT_JSON_LABELS)
                    .requestListDownload(FontDownloader.ApiLinks.GOOGLE_FONTS_API_NO_KEY,
                            apiKey,
                            new FontDownloader.FontListDownloaderCallback() {
                                @Override
                                public void onFontsReceived(String result) {

                                }

                                @Override
                                public void onFontsReceived(List<Font> fonts) {

                                }

                                @Override
                                public void onFailed(Exception e) {

                                }
                            });

            final FontDialogFragment fontDialogFragment = new FontDialogFragment(apiKey);
            fontDialogFragment.showRelativeFontSize();
            fontDialogFragment.setFontListener(new FontDialogFragment.FontListener() {
                @Override
                public void onFontSelectedListener(com.koolio.library.Font lastSelectedFont, Typeface lastSelectedTypeface) {
                    Toast.makeText(MainActivity.this, lastSelectedFont.getFontFamily(), Toast.LENGTH_SHORT).show();
                }
            });

            fontDialogFragment
                    .show(getSupportFragmentManager(), "Fonts");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}