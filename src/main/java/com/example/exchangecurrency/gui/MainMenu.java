package com.example.exchangecurrency.gui;

import com.example.exchangecurrency.entity.MyCurrency;
import com.example.exchangecurrency.entity.myEntry;
import com.example.exchangecurrency.jsony.JsonKonwerter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.json.JSONException;

import java.awt.*;
import java.util.*;
import java.util.List;


@Route("")
public class MainMenu extends VerticalLayout{

    @Autowired
    JsonKonwerter jsonKonwerter;

    List<MyCurrency> myCurrencyList;
    List<String> symbols;
    Map<String,String> resultMap;



    public MainMenu() {

         myCurrencyList = new ArrayList<>();
         symbols = new ArrayList<>();

        String url = "https://openexchangerates.org/api/currencies.json";

        Label lblHello = new Label();
        lblHello.setText("Welcome to exchange rate service. fist get data from:");
        add(lblHello);

        Label lblSource = new Label();
        lblSource.setText(url);
        add(lblSource);

        Button btnUpload = new Button("get list of currencies");

        btnUpload.addClickListener(buttonClickEvent -> {

            Notification notification = new Notification(
                    "We are getting list of currencies", 3000);
            notification.open();


            String json = jsonKonwerter.getJSON(url);
            myCurrencyList = jsonKonwerter.jsonToListCurrencies(json);

           //------------------------------------------------------------------------
               myCurrencyList.stream().forEach(myCurrency -> {
                   symbols.add(myCurrency.getSymbol());
               });

                Collections.sort(myCurrencyList,(s1, s2) -> {
                                    if(s1.getName().compareTo(s2.getName())>1){
                                        return 1;
                                    }
                                    else return -1;
                });

                Collections.sort(symbols);


                Grid<MyCurrency> myCurrencyGrid = new Grid<>(MyCurrency.class);
                myCurrencyGrid.setItems(myCurrencyList);
                myCurrencyGrid.setColumns("symbol","name");
                add(myCurrencyGrid);

                FormLayout nameLayout = new FormLayout();


                Select<String> fromSymbol = new Select<>();
                fromSymbol.setItems(symbols);
                fromSymbol.setValue("USD");
                fromSymbol.setLabel("from:");

                Select<String> toSymbol = new Select<>();
                toSymbol.setItems(symbols);
                toSymbol.setValue("PLN");
                toSymbol.setLabel("to:");
                //add(toSymbol);

                nameLayout.add(fromSymbol,toSymbol);
                add(nameLayout);


                Button btnConvert = new Button("Convert");
                btnConvert.addClickListener(buttonClickEvent1 -> {
                    //------------------------------------------------------------------------
                    String valueFrom = fromSymbol.getValue();
                    String valueTo = toSymbol.getValue();
                    System.out.println("converting from "+valueFrom +" to "+valueTo);

                    //String url2="https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=USD&to_currency=JPY&apikey=demo";
                    //instead of demo you should use your own apikey
                    String url2="https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency="+valueFrom+"&to_currency="+valueTo+"&apikey=demo";
                    String json2 = jsonKonwerter.getJSON(url2);
                    resultMap = jsonKonwerter.jsonToMap(json2);



                    String url2demo="https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency="+valueFrom+"&to_currency="+valueTo+"&apikey=demo";
                    Label labelUrl = new Label(url2demo);
                    add(labelUrl);


                    Map<String, String> sortedMap = new TreeMap<>(resultMap);
                    Set<Map.Entry<String, String>> entries = sortedMap.entrySet();
                    Iterator<Map.Entry<String, String>> resultIterator = entries.iterator();

                   List<myEntry> lista = new ArrayList<>();

                    while(resultIterator.hasNext()) {
                        Map.Entry<String, String> entry = resultIterator.next();
                        System.out.println(entry.getKey()+" "+entry.getValue());
                        lista.add(new myEntry(entry.getKey(),entry.getValue()));

                    }

                    Grid<myEntry> gridResult = new Grid<>(myEntry.class);
                    gridResult.setItems(lista);
                    gridResult.setColumns("description","value");
                    add(gridResult);


                    //------------------------------------------------------------------------
                });
                add(btnConvert);

            //------------------------------------------------------------------------





        });

        add(btnUpload);
    }



}
