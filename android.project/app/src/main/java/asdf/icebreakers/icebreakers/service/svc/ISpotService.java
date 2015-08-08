package asdf.icebreakers.icebreakers.service.svc;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.dto.SpotDTO;

public interface ISpotService {

    /*
    Web API CRUD    SQL
    -------+-------+------
    GET     read    select
    POST    create  insert
    PUT     update  update
    DELETE  delete  delete
     */

    // SQL CREATE
    long insertSpot(SpotDTO dto) throws Exception;
    // SQL READ
    SpotDTO selectSpotById(int value) throws Exception;
    // SQL UPDATE
    int updateSpot(SpotDTO dto) throws Exception;
    // SQL DELETE
    int deleteSpot(SpotDTO dto) throws Exception;
    // SQL LIST
    ArrayList<SpotDTO> selectAllSpots() throws Exception;


    ArrayList<SpotDTO> selectSpotsByState(String value) throws Exception;
}
