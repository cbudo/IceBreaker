package asdf.icebreakers.icebreakers.model.def;

import android.database.Cursor;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.dto.AccountDTO;

public interface IAccountDAO {

    public AccountDTO populateObjectFromCursor(Cursor cursor);

    public long create(AccountDTO dto) throws Exception;
    public AccountDTO read(String key, int value) throws Exception;
    public AccountDTO read(String key, String value) throws Exception;
    public int update(AccountDTO dto) throws Exception;
    public int delete(AccountDTO dto) throws Exception;
    public ArrayList<AccountDTO> list();

    public ArrayList<AccountDTO> list(String key, int value) throws Exception;
    public ArrayList<AccountDTO> list(String key, String value) throws Exception;
    public ArrayList<AccountDTO> list(String[] keys, String[] values) throws Exception;

}
