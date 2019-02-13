package com.example.help.search;

public class HelpHistory {
    private int help_history_id;
    private String help_history_text;

    public HelpHistory() {
    }

    public HelpHistory(int help_history_id, String help_history_text) {
        this.help_history_id = help_history_id;
        this.help_history_text = help_history_text;
    }

    public int getHelp_history_id() {
        return help_history_id;
    }

    public void setHelp_history_id(int help_history_id) {
        this.help_history_id = help_history_id;
    }

    public String getHelp_history_text() {
        return help_history_text;
    }

    public void setHelp_history_text(String help_history_text) {
        this.help_history_text = help_history_text;
    }
}
