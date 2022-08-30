package com.example.demo1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    @FindBy(css = "a.wt-button_mode_primary")
    public WebElement seeAllToolsButton;

    @FindBy(css = "nav > [data-test-marker=\"Developer Tools\"]")
    public WebElement toolsMenu;

    @FindBy(css = "[data-test='site-header-search-action']")
    public WebElement searchButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //this is
}
