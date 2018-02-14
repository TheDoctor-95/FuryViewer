package com.furyviewer.service.TheMovieDB.Service;

import com.furyviewer.domain.Company;
import com.furyviewer.repository.CompanyRepository;
import com.furyviewer.service.TheMovieDB.Repository.CompanyTmdbDTORepository;
import com.furyviewer.service.dto.TheMovieDB.Company.CompleteCompanyTmdbDTO;
import com.furyviewer.service.dto.TheMovieDB.Company.SimpleCompanyTmdbDTO;
import com.furyviewer.service.util.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 * Servicio encargado de recuperar información de una Company desde CompanyTmdbDTORepository y la convierte al
 * formato FuryViewer.
 * @author IFriedkin
 * @see com.furyviewer.service.TheMovieDB.Repository.CompanyTmdbDTORepository
 */
@Service
public class CompanyTmdbDTOService {
    /**
     * Key proporcionada por la api de TheMovieDB para poder hacer peticiones.
     */
    private final String apikey = "08526181d206d48ab49b3fa0be7ad1bf";

    /**
     * Path necesario para poder construir el enlace de la imagen.
     */
    private final String pathImage = "https://image.tmdb.org/t/p/w500";

    /**
     * Se establece conexión para poder hacer peticiones a la api.
     */
    private final CompanyTmdbDTORepository apiTMDB =
        CompanyTmdbDTORepository.retrofit.create(CompanyTmdbDTORepository.class);

    @Autowired
    private CountryService countryService;

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * Devuelve el id de la api de TMDB a partir del nombre de la company.
     * @param companyName String | Nombre de la company a buscar.
     * @return int | id de la company.
     */
    public int getIdTmdbCompany(String companyName) {
        int id = -1;
        SimpleCompanyTmdbDTO company;
        Call<SimpleCompanyTmdbDTO> callCompany = apiTMDB.getSimpleCompany(apikey, companyName);

        try {
            Response<SimpleCompanyTmdbDTO> response = callCompany.execute();

            if (response.isSuccessful()) {
                company = response.body();
                System.out.println(company);
                id = company.getResults().get(0).getId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return id;
    }

    /**
     * Método encargado de convertir la company del formato de TMDB al formato FuryViewer.
     * @param companyName String | Nombre de la company a buscar.
     * @return Company | company en el formato FuryViewer.
     */
    public Company importCompany(String companyName) {
        Company company = new Company();
        company.setName(companyName);

        if (getIdTmdbCompany(companyName) != -1) {
            Call<CompleteCompanyTmdbDTO> callCompany =
                apiTMDB.getCompleteCompany(getIdTmdbCompany(companyName), apikey);

            try {
                Response<CompleteCompanyTmdbDTO> response = callCompany.execute();

                if (response.isSuccessful()) {
                    CompleteCompanyTmdbDTO completeCompanyTmdbDTO = response.body();

                    if (completeCompanyTmdbDTO.getDescription() != null) {
                        company.setDescription(completeCompanyTmdbDTO.getDescription());
                    }

                    if (completeCompanyTmdbDTO.getLogoPath() != null) {
                        company.setImgUrl(pathImage + completeCompanyTmdbDTO.getLogoPath());
                    }

                    if (completeCompanyTmdbDTO.getHeadquarters() != null) {
                        company.setCountry(countryService.importCountry(completeCompanyTmdbDTO.getHeadquarters()));
                    }

                    if (completeCompanyTmdbDTO.getHomepage() != null) {
                        company.setUrlWeb(completeCompanyTmdbDTO.getHomepage());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        company = companyRepository.save(company);

        return company;
    }
}
