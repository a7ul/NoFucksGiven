package rationalduos.atulsoori.nofucksgiven.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import rationalduos.atulsoori.nofucksgiven.cardViews.ImageCard;
import rationalduos.atulsoori.nofucksgiven.cardViews.MarkDownCard;
import rationalduos.atulsoori.nofucksgiven.cardViews.TextCard;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;

import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_IMAGE;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_MARKDOWN;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_TEXT;

/**
 * Created by atulr on 07/07/16.
 */
public class CardTransformer {

    private static ImageCard createImageCard(String id ,String Url) {
        Bundle bundle = new Bundle();
        bundle.putString("cardContent", Url);
        bundle.putString("cardId",id);
        ImageCard mCard = new ImageCard();
        mCard.setArguments(bundle);
        return mCard;
    }

    private static MarkDownCard createMarkDownCard(String id ,String Url) {
        Bundle bundle = new Bundle();
        bundle.putString("cardContent", Url);
        bundle.putString("cardId",id);
        MarkDownCard mkCard = new MarkDownCard();
        mkCard.setArguments(bundle);
        return mkCard;
    }

    private static TextCard createTextCard(String id ,String textContent) {
        Bundle bundle = new Bundle();
        bundle.putString("cardContent", textContent);
        bundle.putString("cardId",id);
        TextCard tCard = new TextCard();
        tCard.setArguments(bundle);
        return tCard;
    }

    public static Fragment cardInfoToFragment(CardInfo cardInfo) throws Exception {

        switch (cardInfo.getType()) {
            case CARD_TYPE_TEXT:
                return createTextCard(cardInfo.getId(),cardInfo.getData());
            case CARD_TYPE_IMAGE:
                return createImageCard(cardInfo.getId(),cardInfo.getData());
            case CARD_TYPE_MARKDOWN:
                return createMarkDownCard(cardInfo.getId(),cardInfo.getData());
            default:
                throw new Exception("Unknown Card Type");
        }
    }

}
