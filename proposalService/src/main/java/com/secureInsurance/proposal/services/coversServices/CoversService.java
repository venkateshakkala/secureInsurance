package com.secureInsurance.proposal.services.coversServices;

import com.secureInsurance.proposal.services.dao.CoversMasterDao;
import com.secureInsurance.proposal.services.model.CoversMaster;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CoversService {
    private CoversMasterDao coversMasterDto;
    public List<CoversMaster> getAllCovers(){
        return coversMasterDto.getAllCovers();
    }
}
