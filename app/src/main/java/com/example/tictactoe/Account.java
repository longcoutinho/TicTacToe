package com.example.tictactoe;

public class Account {
    public String name;
    public String username;
    public String password;
    public int match_win = 0;
    public Account() {
    }

    public Account(String name, String username, String password, int match_win) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.match_win = match_win;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMatch_win() {
        return match_win;
    }

    public void setMatch_win(int match_win) {
        this.match_win = match_win;
    }

    public void incMatch_win() {
        this.match_win++;
    }
}
