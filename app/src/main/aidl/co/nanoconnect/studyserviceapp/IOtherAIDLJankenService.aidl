// IOtherAIDLJankenService.aidl
package co.nanoconnect.studyserviceapp;

interface IOtherAIDLJankenService {
    String getStrMyHand(int inMyHand);
    //  コンピュータの手を生成し、文字列で返す
    String getStrEnmHand();
    //  勝敗を文字列で返す
    String getStrResult();
}
