import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class VersionBean {

    private LatestBean latest;
    private List<VersionsBean> versions;

    @NoArgsConstructor
    @Data
    public static class LatestBean {
        /**
         * release : 1.16.5
         * snapshot : 21w06a
         */

        private String release;
        private String snapshot;
    }

    @NoArgsConstructor
    @Data
    public static class VersionsBean {
        /**
         * id : 21w06a
         * type : snapshot
         * url : https://launchermeta.mojang.com/v1/packages/364987686dc2ab82d114bba868760ff8cd8a7ed4/21w06a.json
         * time : 2021-02-10T17:27:36+00:00
         * releaseTime : 2021-02-10T17:13:54+00:00
         */

        private String id;
        private String type;
        private String url;
        private String time;
        private String releaseTime;
    }
}
