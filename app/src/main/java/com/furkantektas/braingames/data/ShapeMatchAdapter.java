package com.furkantektas.braingames.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.furkantektas.braingames.R;
import com.furkantektas.braingames.datatypes.Game;
import com.furkantektas.braingames.datatypes.ShapeMatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Furkan Tektas on 11/20/14.
 */
public class ShapeMatchAdapter extends BaseAdapter {
    private List<ShapeMatch> mDataSet;
    private Game mGame;
    private View.OnClickListener parentRightOnClickListener;
    private View.OnClickListener parentWrongOnClickListener;
    private View.OnClickListener rightFullSameOnClickListener;
    private View.OnClickListener wrongFullSameOnClickListener;
    private View.OnClickListener rightFullDiffOnClickListener;
    private View.OnClickListener wrongFullDiffOnClickListener;
    private int mInitialSize = 25;
    private int mAnsweredQuestionCount = 0;

    private int mCorrectResults = 0;
    /**
     * Initializes colorMatch Questions adapter with size elements.
     * @param size
     */
    public ShapeMatchAdapter(int size, Game game, final View.OnClickListener rightListener, View.OnClickListener wrongListener) {
        this.mGame = game;
        parentRightOnClickListener = rightListener;
        parentWrongOnClickListener = wrongListener;
        mInitialSize = size;
        mDataSet = new ArrayList<ShapeMatch>((int)(mInitialSize*1.5));
        generateDataset(mInitialSize);
        initListeners();
    }

   private void initListeners() {
        rightFullSameOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++mCorrectResults;
                parentRightOnClickListener.onClick(view);
            }
        };

        wrongFullSameOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentWrongOnClickListener.onClick(view);
            }
        };

        rightFullDiffOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++mCorrectResults;
                parentRightOnClickListener.onClick(view);
            }
        };

        wrongFullDiffOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentWrongOnClickListener.onClick(view);
            }
        };
    }

    /**
     * Extends current dataset by size.
     * @param size
     */
    private void generateDataset(int size) {
        Random r = new Random();
        int startInd = mDataSet.size();
        for(int i = startInd; i < (startInd + size + 1); ++i) {
            int rand1 = r.nextInt(ShapeMatch.Shape.values().length);
            boolean isSame = (i > 0) && ((rand1 % 2) == 0);

            ShapeMatch newShape;
            if(!isSame && i > 0) {
                ShapeMatch prevShape = mDataSet.get(i-1);
                newShape = new ShapeMatch(ShapeMatch.generateShape(rand1));
                while(newShape.equals(prevShape))
                    newShape = new ShapeMatch(ShapeMatch.generateShape(rand1+1));
            } else if(isSame)
                newShape = new ShapeMatch(mDataSet.get(i - 1).shape);
            else
                newShape = new ShapeMatch(ShapeMatch.generateShape(rand1));
            mDataSet.add(newShape);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ++mAnsweredQuestionCount; // constraint: user cannot returned to answered question
        ShapeMatchViewHolder vh;
        if(view == null) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.shape_match_card, viewGroup, false);

            vh = new ShapeMatchViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ShapeMatchViewHolder) view.getTag();
        }
        System.out.println("i="+i);

        ShapeMatch sPrev = null;
        if(i > 0) {
            sPrev = mDataSet.get(i - 1);
            vh.mButtonContainer.setVisibility(View.VISIBLE);
        } else {
            vh.mButtonContainer.setVisibility(View.GONE);
        }

        final ShapeMatch sCurr = mDataSet.get(i);
        vh.mShape.setImageResource(sCurr.shape.getResId());

        if((i+1) == getCount())
            generateDataset(mInitialSize);


         vh.mSameButton.setOnClickListener((sCurr.equals(sPrev) ? rightFullSameOnClickListener : wrongFullSameOnClickListener));
        vh.mDifferentButton.setOnClickListener((!sCurr.equals(sPrev) ? rightFullDiffOnClickListener : wrongFullDiffOnClickListener));

        return view;
    }

    public Game getGame() {
        return mGame;
    }

    public void setGame(Game mGame) {
        this.mGame = mGame;
    }

    public int getAnsweredQuestionCount() {
        return mAnsweredQuestionCount;
    }

    public void setAnsweredQuestionCount(int mAnsweredQuestionCount) {
        this.mAnsweredQuestionCount = mAnsweredQuestionCount;
    }

    public static class ShapeMatchViewHolder {
        public ImageView mShape;
        public Button mSameButton;
        public Button mDifferentButton;
        public View mButtonContainer;

        public ShapeMatchViewHolder(View v) {
            mShape = (ImageView) v.findViewById(R.id.shape);
            mSameButton = (Button) v.findViewById(R.id.button_yes);
            mDifferentButton = (Button) v.findViewById(R.id.button_no);
            mButtonContainer = v.findViewById(R.id.button_container);
        }
    }

    public int getCorrectResults() {
        return mCorrectResults;
    }
}
