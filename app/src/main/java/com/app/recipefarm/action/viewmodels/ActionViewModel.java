package com.app.recipefarm.action.viewmodels;

public class ActionViewModel {
    public int actionId;
    public String title;

    public ActionViewModel(int actionId, String title) {
        this.title = title;
        this.actionId = actionId;
    }
}

