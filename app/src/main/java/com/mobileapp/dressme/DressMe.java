package com.mobileapp.dressme;

//import static android.support.v4.media.session.MediaControllerCompatApi21.getPackageName;
import static android.view.View.VISIBLE;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Arrays;
import java.util.Random;

public class DressMe extends Fragment {

    private DressMeViewModel mViewModel;
    Boolean isGenerated = false;

    public static DressMe newInstance() {
        return new DressMe();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dress_me,
                container, false);
        Button generate = view.findViewById(R.id.generateBtn);
//        ImageView shirt = view.findViewById(R.id.genShirt);
//        ImageView pants = view.findViewById(R.id.genPant);

        LinearLayout layout = view.findViewById(R.id.imgLayout);
        Button regenerate = view.findViewById(R.id.regenerateBtn);
        final String[][] resultItems = new String[1][1];
        Button scrapbookBtn = view.findViewById(R.id.scrapbookBtn);

        //to disable scrapbook & regenerate button if outfit hasn't been generated.
        scrapbookBtn.setEnabled(false);
        regenerate.setEnabled(false);

        //to disable button if no radio button is checked
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        int id = radioGroup.getCheckedRadioButtonId();
        if(id == -1){
            generate.setEnabled(false);
        }
        final String[] resultFile = {""};
        final boolean[] isSeasonChecked = {false};
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                            System.out.println("season");
                switch(checkedId) {
                    case R.id.SpringButton:
                        resultFile[0] = "Spring";
                        isSeasonChecked[0] = true;
                        break;
                    case R.id.SummerButton:
                        resultFile[0] = "Summer";
                        isSeasonChecked[0] = true;
                        break;
                    case R.id.FallButton:
                        resultFile[0] = "Fall";
                        isSeasonChecked[0] = true;
                        break;
                    case R.id.WinterButton:
                        resultFile[0] = "Winter";
                        isSeasonChecked[0] = true;
                        break;
                    default:
                        resultFile[0] = "";
                        isSeasonChecked[0] = false;
                        break;
                }

            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//
//                shirt.setVisibility(view.VISIBLE);
//                pants.setVisibility(view.VISIBLE);
                //read from file to pick random shirt/pant
                //outline for randomizer
                //for now have array of test img ids
                resultItems[0] = outfitGeneration(layout);
                isGenerated = true;
                scrapbookBtn.setEnabled(true);
                regenerate.setEnabled(true);
//                System.out.println(resultItems[0][1]);

            }
        });
        regenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shirt.setVisibility(view.VISIBLE);
//                pants.setVisibility(view.VISIBLE);
                //read from file to pick random shirt/pant
                //outline for randomizer
                //for now have array of test img ids
                layout.removeAllViewsInLayout();
                resultItems[0] = outfitGeneration(layout);

            }
        });
        scrapbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DressMeDirections.ActionDressMeToScrapbook action = DressMeDirections.actionDressMeToScrapbook();
                action.setShirt(resultItems[0][0]);
                action.setPants(resultItems[0][1]);
                Navigation.findNavController(view).navigate(action);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                    generate.setEnabled(true);
            }
        });




        return view;
    }
    public String[] outfitGeneration(LinearLayout layout){
        //these should be read from file or tags
        String[] shirtIds = {"orangeshirt", "testshirt"};
        String[] pantsIds = {"testpants"};
        Random rand = new Random();
        String randShirt = shirtIds[1];
        String randPants = pantsIds[0];
//                for(int i = 0; i < itemIDs.length; i++){
//
//                }
        //dynamically create new imageview w/ clothing image
        ImageView img = new ImageView(layout.getContext());
        int id = 100;
        img.setId(id);
        img.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
//                img.setImageDrawable(getResources().getDrawable(R.drawable.testshirt));
        int resId = getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme");
//                System.out.println("res" + resId);
//                System.out.println(shirtIds[0]);
//                img.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(randShirt, "drawable", "com.mobileapp.dressme")));

        img.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img);

        ImageView img1 = new ImageView(layout.getContext());
        id = 101;
        img1.setId(id);
        img1.setLayoutParams(new android.view.ViewGroup.LayoutParams(500,500));
//                img1.setImageDrawable(getResources().getDrawable(R.drawable.testpants));
        resId = getResources().getIdentifier(randPants, "drawable", "com.mobileapp.dressme");
        img1.setImageDrawable(getResources().getDrawable(resId));
        layout.addView(img1);
        String[] result = {randShirt, randPants};
        return result;
    }
}