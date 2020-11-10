package com.badas.badassolution.Menu;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import java.util.Arrays;
import java.util.Iterator;

public class SheetMenuBuilder {
    private final NavController controller;
    private SheetMenu.DialogMenuListener dialogMenuListener;
    private SheetMenu.SheetMenuItem[] menu = new SheetMenu.SheetMenuItem[0];

    public SheetMenuBuilder(NavController controller) {
        this.controller = controller;
    }

    public SheetMenuBuilder setDialogMenuListener(SheetMenu.DialogMenuListener dialogMenuListener) {
        this.dialogMenuListener = dialogMenuListener;
        return this;
    }

    public SheetMenuBuilder addMenuItem(@IdRes int destinationId) {
        Iterator<NavDestination> destinationIterator = controller.getGraph().iterator();
        NavDestination destination;
        while (destinationIterator.hasNext()) {
            destination = destinationIterator.next();
            if (destination.getId() == destinationId) {
                menu = Arrays.copyOf(menu, menu.length + 1);
                menu[menu.length - 1] = new SheetMenu.SheetMenuItem(destination);
                break;
            }
        }
        return this;
    }

    public SheetMenuBuilder addMenuItem(@IdRes int destinationId, String label) {
        Iterator<NavDestination> destinationIterator = controller.getGraph().iterator();
        NavDestination destination;
        while (destinationIterator.hasNext()) {
            destination = destinationIterator.next();
            if (destination.getId() == destinationId) {
                menu = Arrays.copyOf(menu, menu.length + 1);
                menu[menu.length - 1] = new SheetMenu.SheetMenuItem(destination);
                menu[menu.length - 1].setLabel(label);
                break;
            }
        }
        return this;
    }

    public SheetMenuBuilder addMenuItem(@IdRes int destinationId, @DrawableRes int icon, String label) {
        Iterator<NavDestination> destinationIterator = controller.getGraph().iterator();
        NavDestination destination;
        while (destinationIterator.hasNext()) {
            destination = destinationIterator.next();
            if (destination.getId() == destinationId) {
                menu = Arrays.copyOf(menu, menu.length + 1);
                menu[menu.length - 1] = new SheetMenu.SheetMenuItem(destination);
                menu[menu.length - 1].setIcon(icon);
                menu[menu.length - 1].setLabel(label);
                break;
            }
        }
        return this;
    }

    public SheetMenuBuilder addMenuItem(@IdRes int destinationId, @DrawableRes int icon, String label, int weight) {
        Iterator<NavDestination> destinationIterator = controller.getGraph().iterator();
        NavDestination destination;
        while (destinationIterator.hasNext()) {
            destination = destinationIterator.next();
            if (destination.getId() == destinationId) {
                menu = Arrays.copyOf(menu, menu.length + 1);
                menu[menu.length - 1] = new SheetMenu.SheetMenuItem(destination);
                menu[menu.length - 1].setIcon(icon);
                menu[menu.length - 1].setLabel(label);
                menu[menu.length - 1].setWeight(weight);
                break;
            }
        }
        return this;
    }

    public SheetMenuBuilder addMenuItem(@IdRes int destinationId, @DrawableRes int icon) {
        Iterator<NavDestination> destinationIterator = controller.getGraph().iterator();
        NavDestination destination;
        while (destinationIterator.hasNext()) {
            destination = destinationIterator.next();
            if (destination.getId() == destinationId) {
                menu = Arrays.copyOf(menu, menu.length + 1);
                menu[menu.length - 1] = new SheetMenu.SheetMenuItem(destination);
                menu[menu.length - 1].setIcon(icon);
                break;
            }
        }
        return this;
    }

    public SheetMenuBuilder orderAsc() {
        int n = menu.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (menu[j].getWeight() < menu[j + 1].getWeight()) {
                    SheetMenu.SheetMenuItem temp = menu[j];
                    menu[j] = menu[j + 1];
                    menu[j + 1] = temp;
                }
        return this;
    }

    public SheetMenuBuilder orderDesc() {
        int n = menu.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (menu[j].getWeight() > menu[j + 1].getWeight()) {
                    SheetMenu.SheetMenuItem temp = menu[j];
                    menu[j] = menu[j + 1];
                    menu[j + 1] = temp;
                }
        return this;
    }

    public SheetMenu build() {
        if (controller == null)
            throw new IllegalArgumentException("A NavigationController is required");
        if (menu.length <= 0) {
            Iterator<NavDestination> destinationIterator = controller.getGraph().iterator();
            while (destinationIterator.hasNext()) {
                System.out.println(destinationIterator.toString());
                menu = Arrays.copyOf(menu, menu.length + 1);
                menu[menu.length - 1] = new SheetMenu.SheetMenuItem(destinationIterator.next());
            }
        }
        return new SheetMenu(dialogMenuListener, controller, menu);
    }

}
