package es.solfamidas.elmundo.common.viewmodel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import es.solfamidas.elmundo.R;
import es.solfamidas.elmundo.entities.Article;
import es.solfamidas.elmundo.home.ui.ArticleCardClickListener;
import it.gmariotti.cardslib.library.internal.Card;

public class ArticleCard extends Card {


    protected TextView mTitle;
    protected TextView mDescription;
    protected ImageView mImage;

    protected ArticleCardClickListener listener;

    public Article mArticle;


    /**
     * Constructor with a custom inner layout
     *
     * @param context
     */
    public ArticleCard(Context context) {
        this(context, R.layout.card_layout);
    }

    /**
     * @param context
     * @param innerLayout
     */
    public ArticleCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    public void setListener(ArticleCardClickListener listener) {
        this.listener = listener;
    }

    public void setArticleContent(Article article) {
        mArticle = article;
        setId(mArticle.getGuid());
    }

    /**
     * Init
     */
    private void init() {

        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                listener.onArticleClick(mArticle);
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        parent.setPadding(0, 0, 0, 0);

        //Retrieve elements
        mTitle = (TextView) parent.findViewById(R.id.card_article_title);
        mDescription = (TextView) parent.findViewById(R.id.card_article_desc);
        mImage = (ImageView) parent.findViewById(R.id.card_article_image);

        if (mTitle != null) {
            mTitle.setText(mArticle.getTitle());
        }

        if (mDescription != null) {
            mDescription.setText(mArticle.getSummary());
        }

        if (mImage != null) {
            String thumbnailUrl = "";
            if (mArticle.getImage() != null) {
                thumbnailUrl = mArticle.getImage().getUrl();
                Picasso.with(getContext()).load(thumbnailUrl).resize(800, 500).centerCrop().into(mImage);
            } else {
                mImage.setVisibility(View.GONE);
            }

        }
    }
}

