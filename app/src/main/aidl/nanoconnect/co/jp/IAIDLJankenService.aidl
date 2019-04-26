// IAIDLJankenService.aidl
package nanoconnect.co.jp;

// Declare any non-default types here with import statements

interface IAIDLJankenService {

    String getStrMyHand(int inMyHand);
    //  コンピュータの手を生成し、文字列で返す
    String getStrEnmHand();
    //  勝敗を文字列で返す
    String getStrResult();
}
