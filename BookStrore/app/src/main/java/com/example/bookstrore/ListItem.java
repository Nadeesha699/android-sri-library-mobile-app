package com.example.bookstrore;

public class ListItem {
    private String buttonText;
    private String textView1Text;
    private String textView2Text;
    private String textView3Text;
    private int imageResource;

    public ListItem(String buttonText, String textView1Text, String textView2Text, String textView3Text, int imageResource) {
        this.buttonText = buttonText;
        this.textView1Text = textView1Text;
        this.textView2Text = textView2Text;
        this.textView3Text = textView3Text;
        this.imageResource = imageResource;
    }

    public String getButtonText() {
        return buttonText;
    }

    public String getTextView1Text() {
        return textView1Text;
    }

    public String getTextView2Text() {
        return textView2Text;
    }

    public String getTextView3Text() {
        return textView3Text;
    }

    public int getImageResource() {
        return imageResource;
    }
}

