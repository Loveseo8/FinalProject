package rna.FinalProjectAliceandRoma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class TestView extends AppCompatActivity {

    TextView question;
    RadioGroup radioGroup;
    RadioButton firstOption;
    RadioButton secondOption;
    RadioButton thirdOption;
    RadioButton fourthOption;
    Button next;
    double rightUserAnswersCount = 0;
    int count = 0;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view);

        init();

        final List<String> question0 = new ArrayList<>();
        final List<String> question1 = new ArrayList<>();
        final List<String> question2 = new ArrayList<>();
        final List<String> question3 = new ArrayList<>();
        final List<String> question4 = new ArrayList<>();

        question = findViewById(R.id.questionView);
        radioGroup = findViewById(R.id.radioGroup);
        firstOption = findViewById(R.id.firstOption);
        secondOption = findViewById(R.id.secondOption);
        thirdOption = findViewById(R.id.thirdOption);
        fourthOption = findViewById(R.id.fourthOption);
        next = findViewById(R.id.button_next);

        InputStream inputStream = null;
        try {
            inputStream = getAssets().open(getIntent().getExtras().getString("title") + ".xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = documentBuilder.parse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        Element element = document.getDocumentElement();
        element.normalize();

        NodeList nodeList = document.getElementsByTagName("question0");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String question = element1.getTextContent();

                question0.add(question);

            }

        }

        nodeList = document.getElementsByTagName("question1");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String question = element1.getTextContent();

                question1.add(question);

            }

        }

        nodeList = document.getElementsByTagName("question2");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String question = element1.getTextContent();

                question2.add(question);

            }

        }

        nodeList = document.getElementsByTagName("question3");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String question = element1.getTextContent();

                question3.add(question);

            }

        }


        nodeList = document.getElementsByTagName("question4");
        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element1 = (Element) node;
                String question = element1.getTextContent();

                question4.add(question);

            }

        }

        question.setText(question0.get(0));

        firstOption.setText(question0.get(1));
        secondOption.setText(question0.get(2));
        thirdOption.setText(question0.get(3));
        fourthOption.setText(question0.get(4));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedButton = radioGroup.getCheckedRadioButtonId();

                if (selectedButton == -1) {

                    Snackbar.make(findViewById(R.id.test_view_layout), "Выберите один из предложенных вариантов ответа!", Snackbar.LENGTH_LONG).show();

                } else {

                    RadioButton selected = findViewById(selectedButton);

                    String userAnswer = selected.getText().toString();

                    count++;

                    if (count == 1) {

                        question.setText(question1.get(0));
                        firstOption.setText(question1.get(1));
                        secondOption.setText(question1.get(2));
                        thirdOption.setText(question1.get(3));
                        fourthOption.setText(question1.get(4));

                        if (userAnswer.equals(question0.get(5))) {

                            rightUserAnswersCount++;

                        }

                    } else if (count == 2) {

                        question.setText(question2.get(0));
                        firstOption.setText(question2.get(1));
                        secondOption.setText(question2.get(2));
                        thirdOption.setText(question2.get(3));
                        fourthOption.setText(question2.get(4));

                        if (userAnswer.equals(question1.get(5))) {

                            rightUserAnswersCount++;

                        }

                    } else if (count == 3) {

                        question.setText(question3.get(0));
                        firstOption.setText(question3.get(1));
                        secondOption.setText(question3.get(2));
                        thirdOption.setText(question3.get(3));
                        fourthOption.setText(question3.get(4));

                        if (userAnswer.equals(question2.get(5))) {

                            rightUserAnswersCount++;

                        }

                    } else if (count == 4) {

                        question.setText(question4.get(0));
                        firstOption.setText(question4.get(1));
                        secondOption.setText(question4.get(2));
                        thirdOption.setText(question4.get(3));
                        fourthOption.setText(question4.get(4));

                        if (userAnswer.equals(question3.get(5))) {

                            rightUserAnswersCount++;

                        }

                    } else if (count == 5) {

                        if (userAnswer.equals(question4.get(5))) {

                            rightUserAnswersCount++;

                        }

                        rightUserAnswersCount = rightUserAnswersCount / 5 * 100;
                        int result = (int) Math.floor(rightUserAnswersCount);

                        Intent i = new Intent(TestView.this, TestResult.class);
                        i.putExtra("title", getIntent().getExtras().getString("title"));
                        i.putExtra("result", result + "%");
                        startActivity(i);

                    }

                    radioGroup.clearCheck();

                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Закончить тест");
        builder.setCancelable(false);
        builder.setMessage("Вы не закончили прохождение теста" + ". " + "Уверены, что хотите выйти?");
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                TestView.super.onBackPressed();

            }
        });
        builder.setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("title"));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
