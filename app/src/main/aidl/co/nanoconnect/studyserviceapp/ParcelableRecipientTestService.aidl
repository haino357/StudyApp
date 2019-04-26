// ParcelableRecipientTestService.aidl
package co.nanoconnect.studyserviceapp;

interface ParcelableRecipientTestService {
    //ユーザーの取得
    String getParceUser(String name);
    //メールの取得
    String getParceMail(String mail);
    //URｌの取得
    String getParceURL(String URL);
    //URｌの中身の取得
    String getParceUrlContent(String URL);

    //すべての取得
    //Bundle getAll(Bundle all);

}