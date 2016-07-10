package rationalduos.atulsoori.nofucksgiven.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import rationalduos.atulsoori.nofucksgiven.cardViews.AboutUsFragment;
import rationalduos.atulsoori.nofucksgiven.cardViews.ImageCard;
import rationalduos.atulsoori.nofucksgiven.cardViews.MarkDownCard;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;

import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_ABOUT_US;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_IMAGE;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_TEXT_URL;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_TEXT;

/**
 * Created by atulr on 07/07/16.
 */
public class CardTransformer {

    private static ImageCard createImageCard(CardInfo card) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("cardInfo",card);
        ImageCard mCard = new ImageCard();
        mCard.setArguments(bundle);
        return mCard;
    }

    private static MarkDownCard createMarkDownCard(CardInfo card) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("cardInfo",card);
        MarkDownCard mkCard = new MarkDownCard();
        mkCard.setArguments(bundle);
        return mkCard;
    }

    public static Fragment cardInfoToFragment(CardInfo cardInfo) throws Exception {

        switch (cardInfo.getType()) {
            case CARD_TYPE_TEXT:
                return createMarkDownCard(cardInfo);
            case CARD_TYPE_IMAGE:
                return createImageCard(cardInfo);
            case CARD_TYPE_TEXT_URL:
                return createMarkDownCard(cardInfo);
            case CARD_TYPE_ABOUT_US:
                return new AboutUsFragment();
            default:
                throw new Exception("Unknown Card Type");
        }
    }

}
