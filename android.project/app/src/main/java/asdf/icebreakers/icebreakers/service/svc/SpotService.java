package asdf.icebreakers.icebreakers.service.svc;

import android.content.Context;

import java.util.ArrayList;

import asdf.icebreakers.icebreakers.model.dao.SpotDAO;
import asdf.icebreakers.icebreakers.model.def.ISpotDAO;
import asdf.icebreakers.icebreakers.model.dto.SpotDTO;
import asdf.icebreakers.icebreakers.service.def.ISpotService;

public class SpotService implements ISpotService {

    // Declare an instance of the DAO
    private ISpotDAO dao;

    // Declare a collection
    private ArrayList<SpotDTO> stops;


    /*
    Constructor
     */
    public SpotService(Context ctx) {
        //Instantiate the data access object
        dao = new SpotDAO(ctx);
    }

    @Override
    public long insertSpot(SpotDTO dto) throws Exception {

        long result;

        try {
            result = dao.create(dto);

        } catch (Exception e) {
            e.printStackTrace();
            return -99;
        }

        return result;

    }

    @Override
    public SpotDTO selectSpotById(int value) throws Exception {

        SpotDTO dto;

        try {
            dto = dao.read(SpotDAO.PRIMARY_KEY, value);
            return dto;
        } catch (Exception e) {
            System.out.println( e.toString() );
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateSpot(SpotDTO dto) throws Exception {
        int rows = 0;

        try {
            rows = dao.update(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }

    @Override
    public int deleteSpot(SpotDTO dto) throws Exception {

        int rows = 0;

        rows = dao.delete(dto);

        return rows;

    }

    @Override
    public ArrayList<SpotDTO> selectAllSpots() throws Exception {

        try {
            stops = dao.list();
            return stops;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ArrayList<SpotDTO> selectSpotsByState(String value) throws Exception {

        ArrayList<SpotDTO> list = dao.list(SpotDAO.SPOT_STATE, value);

        return list;

    }
}