package com.badas.badassolution;

import android.graphics.drawable.Drawable;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project: BadasSolution
 * By: Seanf
 * Created: 05,October,2020
 */
public class DynamicMenu {
    private static final DynamicMenu Instance = new DynamicMenu();

    static DynamicMenu getInstance() {
        return Instance;
    }

    private DynamicMenu() {
    }

    private static Object[] menuItems = new Object[0];

    public void add(MenuItem menuItem) {
        for (Object item: menuItems) {
            if (item.getClass() == MenuItem.class){
                MenuItem menuItem1 = (MenuItem) item;
                if (menuItem1.getId() == menuItem.getId()){
                    System.out.println("Item already exist: " + menuItem.getTitle());
                    return;
                }
            }
        }
        menuItems = Arrays.copyOf(menuItems, menuItems.length+1);
        menuItems[menuItems.length-1] = menuItem;
    }

    public void add(SubMenu subMenu) {
        for (Object item: menuItems) {
            if (item.getClass() == SubMenu.class){
                SubMenu sub = (SubMenu) item;
                if (subMenu.getId() == sub.getId()){
                    System.out.println("Item already exist: " + sub.getTitle());
                    return;
                }
            }
        }
        menuItems = Arrays.copyOf(menuItems, menuItems.length+1);
        menuItems[menuItems.length-1] = subMenu;
    }

    public void attach(NavigationView navigationView) {
        for (Object item : menuItems) {
            if (item.getClass() == MenuItem.class){
                MenuItem menuItem = (MenuItem) item;
                navigationView.getMenu().add(menuItem.getGroup(),menuItem.getId(),menuItem.getOrder(),menuItem.getTitle());
                navigationView.getMenu().setGroupCheckable(((MenuItem) item).getGroup(),true,true);
                navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setIcon(menuItem.icon);
            } else {
                SubMenu subMenu = (SubMenu) item;
                android.view.SubMenu menu = navigationView.getMenu().addSubMenu(subMenu.getGroup(),subMenu.getId(),subMenu.getOrder(),subMenu.getTitle());
                navigationView.getMenu().setGroupCheckable(subMenu.getGroup(),true,true);
                subMenu.attach(menu);
            }
        }
    }

    public int[] getIDs(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Object menuItem : menuItems) {
            if (menuItem.getClass() == MenuItem.class)
                ids.add(((MenuItem) menuItem).getId());
            else {
                for (int id : ((SubMenu) menuItem).getIDs()) {
                    ids.add(id);
                }
            }
        }
        int[] idArray = new int[ids.size()];
        for (int i = 0; i < idArray.length; i++) {
            idArray[i] = ids.get(i);
        }
        return idArray;
    }

    public static class SubMenu {
        private int group = 1;
        private int order = 0;
        private String title = "";
        private int id;
        private Object[] subMenuItems = new Object[0];

        public int[] getIDs(){
            ArrayList<Integer> ids = new ArrayList<>();
            for (Object menuItem : subMenuItems) {
                if (menuItem.getClass() == MenuItem.class)
                    ids.add(((MenuItem) menuItem).getId());
                else {
                    for (int id : ((SubMenu) menuItem).getIDs()) {
                        ids.add(id);
                    }
                }
            }
            int[] idArray = new int[ids.size()];
            for (int i = 0; i < idArray.length; i++) {
                idArray[i] = ids.get(i);
            }
            return idArray;
        }

        public void attach(android.view.SubMenu menu) {
            for (Object item : subMenuItems) {
                if (item.getClass() == MenuItem.class){
                    MenuItem menuItem = (MenuItem) item;
                    menu.add(menuItem.getGroup(),menuItem.getId(),menuItem.getOrder(),menuItem.getTitle());
                    menu.setGroupCheckable(((MenuItem) item).getGroup(),true,true);
                    menu.getItem(menu.size()-1).setIcon(menuItem.icon);
                } else {
                    SubMenu subMenu = (SubMenu) item;
                    subMenu.attach(menu.addSubMenu(subMenu.getGroup(),subMenu.getId(),subMenu.getOrder(),subMenu.getTitle()));
                }
            }
        }

        public void add(MenuItem... subMenuItems) {
            loopContinue:
            for (MenuItem menuItem: subMenuItems) {
                for (Object item: subMenuItems) {
                    if (item.getClass() == MenuItem.class){
                        MenuItem menuItem1 = (MenuItem) item;
                        if (menuItem1.getId() == menuItem.getId()){
                            System.out.println("Item already exist: " + menuItem.getTitle());
                            break loopContinue;
                        }
                    }
                }
                subMenuItems = Arrays.copyOf(subMenuItems, subMenuItems.length+1);
                subMenuItems[subMenuItems.length-1] = menuItem;
            }

        }

        public SubMenu add(MenuItem subMenuItem) {
            for (Object item: subMenuItems) {
                if (item.getClass() == MenuItem.class){
                    MenuItem menuItem1 = (MenuItem) item;
                    if (menuItem1.getId() == subMenuItem.getId()){
                        System.out.println("Item already exist: " + subMenuItem.getTitle());
                        return this;
                    }
                }
            }
            subMenuItems = Arrays.copyOf(subMenuItems, subMenuItems.length+1);
            subMenuItems[subMenuItems.length-1] = subMenuItem;
            return this;
        }

        public SubMenu(int id) {
            this.id = id;
        }

        public int getGroup() {
            return group;
        }

        public SubMenu setGroup(int group) {
            this.group = group;
            return this;
        }

        public int getOrder() {
            return order;
        }

        public SubMenu setOrder(int order) {
            this.order = order;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public SubMenu setTitle(String title) {
            this.title = title;
            return this;
        }

        public int getId() {
            return id;
        }

        public SubMenu setId(int id) {
            this.id = id;
            return this;
        }
    }

    public static class MenuItem{
        private Drawable icon;
        private int group = 0;
        private int order = 0;
        private String title = "placeholder";
        private int id;

        public MenuItem(int id) {
            this.id = id;
        }

        public int getGroup() {
            return group;
        }

        public MenuItem setGroup(int group) {
            this.group = group;
            return this;
        }

        public int getOrder() {
            return order;
        }

        public MenuItem setOrder(int order) {
            this.order = order;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public MenuItem setTitle(String title) {
            this.title = title;
            return this;
        }

        public int getId() {
            return id;
        }

        public MenuItem setId(int id) {
            this.id = id;
            return this;
        }

        public Drawable getIcon() {
            return icon;
        }

        public MenuItem setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }
    }
}
