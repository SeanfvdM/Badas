package com.badas.badassolution;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.badas.badassolution.Menu.SheetMenu;
import com.badas.badassolution.Menu.SheetMenuBuilder;
import com.badas.gamelibrary.ColorGame;
import com.badas.gamelibrary.GameFragmentTemplate;
import com.badas.profilemanager.ManagerFragment;
import com.badas.profilemanager.Profile;
import com.badas.studentactivity.Student;
import com.badas.studentactivity.StudentActivityFragment;
import com.badas.studentresults.ResultFragment;
import com.badas.studentresults.StudentResult;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    static FloatingActionButton floatingActionButton;
    boolean showFab = false, show = true;
    BottomAppBar bottomAppBar;
    NavHostFragment navHostFragment;
    SheetMenu sheetMenu;

    public void showAppbar() {
        if (show) {
            bottomAppBar.performShow();
            if (showFab)
                floatingActionButton.show();
        }
    }

    public void hideAppbar() {
        bottomAppBar.performHide();
        floatingActionButton.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Not implemented", BaseTransientBottomBar.LENGTH_SHORT).setAnchorView(v).show();
            }
        });

        @IdRes int homeId = R.id.nav_home;
        if (navHostFragment.getNavController().getCurrentDestination() == null)
            navHostFragment.getNavController().navigate(homeId);

        assert navHostFragment != null;
        navHostFragment.getNavController().popBackStack(homeId, false);

        sheetMenu = new SheetMenuBuilder(navHostFragment.getNavController())
                .setDialogMenuListener(new SheetMenu.DialogMenuListener() {
                    @Override
                    public void onDismissed(boolean stateLost) {
                        showAppbar();
                    }

                    @Override
                    public void onCanceled() {
                        showAppbar();
                    }
                })
                .addMenuItem(homeId, R.drawable.ic_home, getString(R.string.menu_home), 0)
                .addMenuItem(R.id.nav_result, R.drawable.ic_result, getString(R.string.menu_result), 0)
                .addMenuItem(R.id.nav_student_result, R.drawable.ic_result_filter, getString(R.string.menu_student_result), 0)
                .addMenuItem(R.id.nav_profile_manager, R.drawable.ic_work, getString(R.string.menu_profile_manager), 0)
                .addMenuItem(R.id.nav_game_manager, R.drawable.ic_baseline_gamepad_24, getString(R.string.menu_game_manager), 0)
//                .addMenuItem(R.id.nav_game_template, R.drawable.ic_outline_bug_report_24, "Color Game (WIP)", 0)
                .orderAsc()
                .build();

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideAppbar();
                sheetMenu.show(MainActivity2.this.getSupportFragmentManager(), "menu");
            }
        });

        navHostFragment.getNavController().addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (!bottomAppBar.isShown())
                    show = true;
                bottomAppBar.setHideOnScroll(true);
                if (destination.getId() == R.id.nav_result || destination.getId() == R.id.nav_student_result) {
                    showFab = false;
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
                    ResultFragment.setFloatingActionButton(floatingActionButton);
                    bottomAppBar.setHideOnScroll(false);
                } else if (destination.getId() == R.id.nav_student_activity) {
                    showFab = false;
                    ArrayList<Student> students = new ArrayList<>();
                    students.add(new Student("Sean", new Date(), (long) 500000, "test", "test"));
                    students.add(new Student("Aharon", new Date(), (long) 1500000, "test2", "test"));
                    students.add(new Student("Daniel", new Date(), (long) 10000, "test", "test3"));
                    students.add(new Student("Brandon", new Date(), (long) 506300, "test4", "test4"));
                    students.add(new Student("Avremi", new Date(), (long) 1000, "test5", "test5"));

                    StudentActivityFragment.initDataSet(students);
                } else if (destination.getId() == R.id.nav_profile_manager) {
                    showFab = true;
                    ArrayList<Profile> userList = new ArrayList<>();

                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Aharon", "Email:\talexkangy@gmail.com \nPhone Number:\t0749597678"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Sean", "Email:\tSean@gmail.com \nPhone Number:\t0825412578"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Daniel", "Email:\tDaniel@gmail.com \nPhone Number:\t0720215128"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Brandon", "Email:\tBrandon@gmail.com \nPhone Number:\t0832514325"));
                    userList.add(new Profile(com.badas.profilemanager.R.drawable.profile_icon_templete, "Avremi", "Email:\tAvremi@gmail.com \nPhone Number:\t0821986254"));

                    ManagerFragment.initDataSet(userList);
                    ManagerFragment.setFloatingActionButton(floatingActionButton);
                } else if (destination.getId() == R.id.nav_game_template) {
                    showFab = false;
                    GameFragmentTemplate.init(
                            new ColorGame(),
                            new GameFragmentTemplate.GameCallback() {
                                @Override
                                public void parentCalled(AppCompatActivity compatActivity) {
                                    super.parentCalled(compatActivity);
                                    show = false;
                                    hideAppbar();
                                }

                                @Override
                                public void doneClicked() {
                                    show = true; //todo change this for final
                                    showAppbar();
                                    super.doneClicked();
                                }
                            });
                    GameFragmentTemplate.getGameCallback().parentCalled(MainActivity2.this);
                } else {
                    showFab = false;
                }
                showAppbar();
            }
        });
    }
}