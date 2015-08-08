package asdf.icebreakers.icebreakers.model.def;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.dto.SpotDTO;

public interface ISpotDAO {

    long create(SpotDTO dto) throws Exception;
    SpotDTO read(String key, int value) throws Exception;
    SpotDTO read(String key, String value) throws Exception;
    int update(SpotDTO dto) throws Exception;
    int delete(SpotDTO dto) throws Exception;

    ArrayList<SpotDTO> list() throws Exception;
    ArrayList<SpotDTO> list(String key, int value) throws Exception;
    ArrayList<SpotDTO> list(String key, String value) throws Exception;
    ArrayList<SpotDTO> list(String[] keys, String[] values) throws Exception;
    ArrayList<SpotDTO> list(String[] keys, int[] values) throws Exception;

    int count(String key, int value) throws Exception;

}
