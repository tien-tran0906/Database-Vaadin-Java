package com.example.demo; //Vaadin allows to build a web app fully in java/ No HTML or JavaScript but the browser is going to take care of that at the end

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;


import java.awt.*;
import java.util.Collection;

// You can also use HorizontalLayout()
// This is going to be the UI

@Route("")
public class MainView extends VerticalLayout { // Means any components that we put in here is going to get stacked vertically
    private final PersonRepo repo; //save this so that we have access throughout the UI
    private final TextField  firstName = new TextField("First name");
    private final TextField  lastName = new TextField("Last name");
    private final EmailField email = new EmailField("Email"); // Validates that we are inputting an email

    private final Binder<Person> binder = new Binder<>(Person.class); //To link info from the back end to front end
    private final Grid<Person> grid = new Grid<>(Person.class); //Show to UI info in form of a grid




    //Method
    public MainView(PersonRepo repo) {
        /*add(new H1("Hello World!")); // Needs import on line 3 to do this
        var button = new Button("Click me");
        var textField = new TextField();

        add(textField, button);

        button.addClickListener(e -> { //event listener
            add(new Paragraph("Hello, " + textField.getValue())); //get value from the text field
            textField.clear();
        });*/

        // Form for entering first, last name and email

        this.repo = repo;
        add(getForm(), grid);
        grid.setColumns("firstName", "lastName", "email"); // reset the order of the columns
        refreshGrid();


    }

    private Component getForm() { // Can't return type String, has to be Component so we can add?
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE); // Set alignment to baseline
        var button = new Button ("Add");
        button.addClickShortcut(Key.ENTER); // Allows user to press enter as well, instead of just clicking "add" button
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(firstName, lastName, email, button);

        binder.bindInstanceFields(this); // Binds the variables from Person to this file
        button.addClickListener(click -> {
           try {
               var person = new Person(); //
               binder.writeBean(person);
               repo.save(person);
               refreshGrid();
               binder.readBean(new Person()); // Refreshes the text field when they click "Add"
           } catch (Exception e) {

           }
        });


        return layout;

    }

    private void refreshGrid() {
        grid.setItems(repo.findAll()); // Find everything in the repo and show it to the grid
    }
}