import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class GameBean {

    private ArgumentsBean arguments;
    private AssetIndexBean assetIndex;
    private String assets;
    private Integer complianceLevel;
    private DownloadsBean downloads;
    private String id;
    private List<LibrariesBean> libraries;
    private LoggingBean logging;
    private String mainClass;
    private Integer minimumLauncherVersion;
    private String releaseTime;
    private String time;
    private String type;

    @NoArgsConstructor
    @Data
    public static class ArgumentsBean {
        private List<String> game;
        private List<JvmBean> jvm;

        @NoArgsConstructor
        @Data
        public static class JvmBean {
            private List<RulesBean> rules;
            private List<String> value;

            @NoArgsConstructor
            @Data
            public static class RulesBean {
                /**
                 * action : allow
                 * os : {"name":"osx"}
                 */

                private String action;
                private OsBean os;

                @NoArgsConstructor
                @Data
                public static class OsBean {
                    /**
                     * name : osx
                     */

                    private String name;
                }
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class AssetIndexBean {
        /**
         * id : 1.17
         * sha1 : 358623ca0695a0e1180013039011b77bfc03d6d8
         * size : 334132
         * totalSize : 341041859
         * url : https://launchermeta.mojang.com/v1/packages/358623ca0695a0e1180013039011b77bfc03d6d8/1.17.json
         */

        private String id;
        private String sha1;
        private Integer size;
        private Integer totalSize;
        private String url;
    }

    @NoArgsConstructor
    @Data
    public static class DownloadsBean {
        /**
         * client : {"sha1":"50a88791b64547d5325018270e0a5a71f8d4fc03","size":18479570,"url":"https://launcher.mojang.com/v1/objects/50a88791b64547d5325018270e0a5a71f8d4fc03/client.jar"}
         * client_mappings : {"sha1":"2f99b29b39bb18ac67677d7876859c09cc1f85a3","size":6165888,"url":"https://launcher.mojang.com/v1/objects/2f99b29b39bb18ac67677d7876859c09cc1f85a3/client.txt"}
         * server : {"sha1":"6290ba4b475fca4a74de990c7fd8eccffd9654dd","size":38486691,"url":"https://launcher.mojang.com/v1/objects/6290ba4b475fca4a74de990c7fd8eccffd9654dd/server.jar"}
         * server_mappings : {"sha1":"c4e373406d2166580c33b075c2d05d9d2fb18d43","size":4732408,"url":"https://launcher.mojang.com/v1/objects/c4e373406d2166580c33b075c2d05d9d2fb18d43/server.txt"}
         */

        private ClientBean client;
        private ClientMappingsBean client_mappings;
        private ServerBean server;
        private ServerMappingsBean server_mappings;

        @NoArgsConstructor
        @Data
        public static class ClientBean {
            /**
             * sha1 : 50a88791b64547d5325018270e0a5a71f8d4fc03
             * size : 18479570
             * url : https://launcher.mojang.com/v1/objects/50a88791b64547d5325018270e0a5a71f8d4fc03/client.jar
             */

            private String sha1;
            private Integer size;
            private String url;
        }

        @NoArgsConstructor
        @Data
        public static class ClientMappingsBean {
            /**
             * sha1 : 2f99b29b39bb18ac67677d7876859c09cc1f85a3
             * size : 6165888
             * url : https://launcher.mojang.com/v1/objects/2f99b29b39bb18ac67677d7876859c09cc1f85a3/client.txt
             */

            private String sha1;
            private Integer size;
            private String url;
        }

        @NoArgsConstructor
        @Data
        public static class ServerBean {
            /**
             * sha1 : 6290ba4b475fca4a74de990c7fd8eccffd9654dd
             * size : 38486691
             * url : https://launcher.mojang.com/v1/objects/6290ba4b475fca4a74de990c7fd8eccffd9654dd/server.jar
             */

            private String sha1;
            private Integer size;
            private String url;
        }

        @NoArgsConstructor
        @Data
        public static class ServerMappingsBean {
            /**
             * sha1 : c4e373406d2166580c33b075c2d05d9d2fb18d43
             * size : 4732408
             * url : https://launcher.mojang.com/v1/objects/c4e373406d2166580c33b075c2d05d9d2fb18d43/server.txt
             */

            private String sha1;
            private Integer size;
            private String url;
        }
    }

    @NoArgsConstructor
    @Data
    public static class LoggingBean {
        /**
         * client : {"argument":"-Dlog4j.configurationFile=${path}","file":{"id":"client-1.12.xml","sha1":"ef4f57b922df243d0cef096efe808c72db042149","size":877,"url":"https://launcher.mojang.com/v1/objects/ef4f57b922df243d0cef096efe808c72db042149/client-1.12.xml"},"type":"log4j2-xml"}
         */

        private ClientBean client;

        @NoArgsConstructor
        @Data
        public static class ClientBean {
            /**
             * argument : -Dlog4j.configurationFile=${path}
             * file : {"id":"client-1.12.xml","sha1":"ef4f57b922df243d0cef096efe808c72db042149","size":877,"url":"https://launcher.mojang.com/v1/objects/ef4f57b922df243d0cef096efe808c72db042149/client-1.12.xml"}
             * type : log4j2-xml
             */

            private String argument;
            private FileBean file;
            private String type;

            @NoArgsConstructor
            @Data
            public static class FileBean {
                /**
                 * id : client-1.12.xml
                 * sha1 : ef4f57b922df243d0cef096efe808c72db042149
                 * size : 877
                 * url : https://launcher.mojang.com/v1/objects/ef4f57b922df243d0cef096efe808c72db042149/client-1.12.xml
                 */

                private String id;
                private String sha1;
                private Integer size;
                private String url;
            }
        }
    }

    @NoArgsConstructor
    @Data
    public static class LibrariesBean {
        /**
         * downloads : {"artifact":{"path":"com/mojang/patchy/1.1/patchy-1.1.jar","sha1":"aef610b34a1be37fa851825f12372b78424d8903","size":15817,"url":"https://libraries.minecraft.net/com/mojang/patchy/1.1/patchy-1.1.jar"}}
         * name : com.mojang:patchy:1.1
         * rules : [{"action":"allow","os":{"name":"osx"}}]
         * natives : {"osx":"natives-macos"}
         * extract : {"exclude":["META-INF/"]}
         */

        private DownloadsBean downloads;
        private String name;
        private List<RulesBean> rules;
        private NativesBean natives;
        private ExtractBean extract;

        @NoArgsConstructor
        @Data
        public static class DownloadsBean {
            /**
             * artifact : {"path":"com/mojang/patchy/1.1/patchy-1.1.jar","sha1":"aef610b34a1be37fa851825f12372b78424d8903","size":15817,"url":"https://libraries.minecraft.net/com/mojang/patchy/1.1/patchy-1.1.jar"}
             */

            private ArtifactBean artifact;

            @NoArgsConstructor
            @Data
            public static class ArtifactBean {
                /**
                 * path : com/mojang/patchy/1.1/patchy-1.1.jar
                 * sha1 : aef610b34a1be37fa851825f12372b78424d8903
                 * size : 15817
                 * url : https://libraries.minecraft.net/com/mojang/patchy/1.1/patchy-1.1.jar
                 */

                private String path;
                private String sha1;
                private Integer size;
                private String url;
            }
        }

        @NoArgsConstructor
        @Data
        public static class NativesBean {
            /**
             * osx : natives-macos
             */

            private String osx;
        }

        @NoArgsConstructor
        @Data
        public static class ExtractBean {
            private List<String> exclude;
        }

        @NoArgsConstructor
        @Data
        public static class RulesBean {
            /**
             * action : allow
             * os : {"name":"osx"}
             */

            private String action;
            private OsBean os;

            @NoArgsConstructor
            @Data
            public static class OsBean {
                /**
                 * name : osx
                 */

                private String name;
            }
        }
    }
}
