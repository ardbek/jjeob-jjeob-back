package utils;

import lombok.extern.slf4j.Slf4j;
import org.locationtech.proj4j.*;

@Slf4j
public class LocationUtils {

    private final String EPSG_5174_PARAMS = "+proj=tmerc +lat_0=38 +lon_0=127.0028902777778 +k=1 +x_0=200000 +y_0=500000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43";
    private final String WGS84CRS = "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs";

    /**
     * EPSG:5174 좌표계 값을 전달받아 WGS84 좌표계 값으로 반환
     * @param x
     * @param y
     * @return ProjCoordinate
     */
    public ProjCoordinate CoordinateConversion(String x, String y) {

        double d_x = Double.parseDouble(x);
        double d_y = Double.parseDouble(y);

        CRSFactory crsFactory = new CRSFactory();

        CoordinateReferenceSystem sourceCRS = crsFactory.createFromParameters("EPSG:5174", EPSG_5174_PARAMS);
        CoordinateReferenceSystem targetCRS = crsFactory.createFromParameters("EPSG:4326", WGS84CRS);

        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform transform = ctFactory.createTransform(sourceCRS, targetCRS);

        ProjCoordinate tmCoord = new ProjCoordinate(d_x, d_y);
        ProjCoordinate wgs84Coord = new ProjCoordinate();

        transform.transform(tmCoord, wgs84Coord);
        log.info("LocationUtils :: {},{}",wgs84Coord.x, wgs84Coord.y);
        return wgs84Coord;
    }


}
