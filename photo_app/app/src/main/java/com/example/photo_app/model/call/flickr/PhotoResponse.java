package com.example.photo_app.model.call.flickr;

public class PhotoResponse {
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String Id;
    private String Owner;
    private String Secret;
    private String Server;
    private String Sarm;
    private String Sitle;
    private boolean IsPublic;
    private boolean IsFriend;
    private boolean IsFamily;

    private String UrlO;
    private int HeightO;
    private int WidthO;

    private String Description;
    private String License;
    private String DateUpload;
    private String DateTaken;
    private String OwnerName;
    private String IconServer;
    private String OriginalFormat;
    private String LastUpdate;

    // Geo - these attributes are provided when extras contains "geo"
    private String Latitude;
    private String Longitude;
    private String Accuracy;
    private String Context;

    // Tags - contains space-separated lists
    private String Tags;
    private String MachineTags;

    // Original Dimensions - these attributes are provided
    // when extras contains "o_dims"
    private String OWidth;
    private String OHeight;

    private int Views;
    private String Media;
    private String PathAlias;

    // Square Urls - these attributes are provided when
    // extras contains "url_sq"
    private String UrlSq;
    private int HeightSq;
    private int WidthSq;

    // Thumbnail Urls - these attributes are provided
    // when extras contains "url_t"
    private String UrlT;
    private int HeightT;
    private int WidthT;


    // Q Urls - these attributes are provided when
    // extras contains "url_s"
    private String UrlS;
    private int HeightS;
    private int WidthS;

    // M Urls - these attributes are provided when
    // extras contains "url_m"
    private String UrlM;
    private int HeightM;
    private int WidthM;

    // N Urls - these attributes are provided when
    // extras contains "url_n"
    private String UrlN;
    private int HeightN;
    private int WidthN;

    // Z Urls - these attributes are provided when
    // extras contains "url_z"
    private String UrlZ;
    private int HeightZ;
    private int WidthZ;

    // C Urls - these attributes are provided when
    // extras contains "url_c"
    private String UrlC;
    private int HeightC;
    private int WidthC;

    // L Urls - these attributes are provided when
    // extras contains "url_l"
    private String UrlL;
    private int HeightL;
    private int WidthL;

    public PhotoResponse() {
    }

    public PhotoResponse(String created_at, String updated_at, String deleted_at, String id, String owner, String secret, String server, String sarm, String sitle, boolean isPublic, boolean isFriend, boolean isFamily, String urlO, int heightO, int widthO, String description, String license, String dateUpload, String dateTaken, String ownerName, String iconServer, String originalFormat, String lastUpdate, String latitude, String longitude, String accuracy, String context, String tags, String machineTags, String OWidth, String OHeight, int views, String media, String pathAlias, String urlSq, int heightSq, int widthSq, String urlT, int heightT, int widthT, String urlS, int heightS, int widthS, String urlM, int heightM, int widthM, String urlN, int heightN, int widthN, String urlZ, int heightZ, int widthZ, String urlC, int heightC, int widthC, String urlL, int heightL, int widthL) {
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        Id = id;
        Owner = owner;
        Secret = secret;
        Server = server;
        Sarm = sarm;
        Sitle = sitle;
        IsPublic = isPublic;
        IsFriend = isFriend;
        IsFamily = isFamily;
        UrlO = urlO;
        HeightO = heightO;
        WidthO = widthO;
        Description = description;
        License = license;
        DateUpload = dateUpload;
        DateTaken = dateTaken;
        OwnerName = ownerName;
        IconServer = iconServer;
        OriginalFormat = originalFormat;
        LastUpdate = lastUpdate;
        Latitude = latitude;
        Longitude = longitude;
        Accuracy = accuracy;
        Context = context;
        Tags = tags;
        MachineTags = machineTags;
        this.OWidth = OWidth;
        this.OHeight = OHeight;
        Views = views;
        Media = media;
        PathAlias = pathAlias;
        UrlSq = urlSq;
        HeightSq = heightSq;
        WidthSq = widthSq;
        UrlT = urlT;
        HeightT = heightT;
        WidthT = widthT;
        UrlS = urlS;
        HeightS = heightS;
        WidthS = widthS;
        UrlM = urlM;
        HeightM = heightM;
        WidthM = widthM;
        UrlN = urlN;
        HeightN = heightN;
        WidthN = widthN;
        UrlZ = urlZ;
        HeightZ = heightZ;
        WidthZ = widthZ;
        UrlC = urlC;
        HeightC = heightC;
        WidthC = widthC;
        UrlL = urlL;
        HeightL = heightL;
        WidthL = widthL;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getSecret() {
        return Secret;
    }

    public void setSecret(String secret) {
        Secret = secret;
    }

    public String getServer() {
        return Server;
    }

    public void setServer(String server) {
        Server = server;
    }

    public String getSarm() {
        return Sarm;
    }

    public void setSarm(String sarm) {
        Sarm = sarm;
    }

    public String getSitle() {
        return Sitle;
    }

    public void setSitle(String sitle) {
        Sitle = sitle;
    }

    public boolean isPublic() {
        return IsPublic;
    }

    public void setPublic(boolean aPublic) {
        IsPublic = aPublic;
    }

    public boolean isFriend() {
        return IsFriend;
    }

    public void setFriend(boolean friend) {
        IsFriend = friend;
    }

    public boolean isFamily() {
        return IsFamily;
    }

    public void setFamily(boolean family) {
        IsFamily = family;
    }

    public String getUrlO() {
        return UrlO;
    }

    public void setUrlO(String urlO) {
        UrlO = urlO;
    }

    public int getHeightO() {
        return HeightO;
    }

    public void setHeightO(int heightO) {
        HeightO = heightO;
    }

    public int getWidthO() {
        return WidthO;
    }

    public void setWidthO(int widthO) {
        WidthO = widthO;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public String getDateUpload() {
        return DateUpload;
    }

    public void setDateUpload(String dateUpload) {
        DateUpload = dateUpload;
    }

    public String getDateTaken() {
        return DateTaken;
    }

    public void setDateTaken(String dateTaken) {
        DateTaken = dateTaken;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getIconServer() {
        return IconServer;
    }

    public void setIconServer(String iconServer) {
        IconServer = iconServer;
    }

    public String getOriginalFormat() {
        return OriginalFormat;
    }

    public void setOriginalFormat(String originalFormat) {
        OriginalFormat = originalFormat;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(String accuracy) {
        Accuracy = accuracy;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getMachineTags() {
        return MachineTags;
    }

    public void setMachineTags(String machineTags) {
        MachineTags = machineTags;
    }

    public String getOWidth() {
        return OWidth;
    }

    public void setOWidth(String OWidth) {
        this.OWidth = OWidth;
    }

    public String getOHeight() {
        return OHeight;
    }

    public void setOHeight(String OHeight) {
        this.OHeight = OHeight;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }

    public String getPathAlias() {
        return PathAlias;
    }

    public void setPathAlias(String pathAlias) {
        PathAlias = pathAlias;
    }

    public String getUrlSq() {
        return UrlSq;
    }

    public void setUrlSq(String urlSq) {
        UrlSq = urlSq;
    }

    public int getHeightSq() {
        return HeightSq;
    }

    public void setHeightSq(int heightSq) {
        HeightSq = heightSq;
    }

    public int getWidthSq() {
        return WidthSq;
    }

    public void setWidthSq(int widthSq) {
        WidthSq = widthSq;
    }

    public String getUrlT() {
        return UrlT;
    }

    public void setUrlT(String urlT) {
        UrlT = urlT;
    }

    public int getHeightT() {
        return HeightT;
    }

    public void setHeightT(int heightT) {
        HeightT = heightT;
    }

    public int getWidthT() {
        return WidthT;
    }

    public void setWidthT(int widthT) {
        WidthT = widthT;
    }

    public String getUrlS() {
        return UrlS;
    }

    public void setUrlS(String urlS) {
        UrlS = urlS;
    }

    public int getHeightS() {
        return HeightS;
    }

    public void setHeightS(int heightS) {
        HeightS = heightS;
    }

    public int getWidthS() {
        return WidthS;
    }

    public void setWidthS(int widthS) {
        WidthS = widthS;
    }

    public String getUrlM() {
        return UrlM;
    }

    public void setUrlM(String urlM) {
        UrlM = urlM;
    }

    public int getHeightM() {
        return HeightM;
    }

    public void setHeightM(int heightM) {
        HeightM = heightM;
    }

    public int getWidthM() {
        return WidthM;
    }

    public void setWidthM(int widthM) {
        WidthM = widthM;
    }

    public String getUrlN() {
        return UrlN;
    }

    public void setUrlN(String urlN) {
        UrlN = urlN;
    }

    public int getHeightN() {
        return HeightN;
    }

    public void setHeightN(int heightN) {
        HeightN = heightN;
    }

    public int getWidthN() {
        return WidthN;
    }

    public void setWidthN(int widthN) {
        WidthN = widthN;
    }

    public String getUrlZ() {
        return UrlZ;
    }

    public void setUrlZ(String urlZ) {
        UrlZ = urlZ;
    }

    public int getHeightZ() {
        return HeightZ;
    }

    public void setHeightZ(int heightZ) {
        HeightZ = heightZ;
    }

    public int getWidthZ() {
        return WidthZ;
    }

    public void setWidthZ(int widthZ) {
        WidthZ = widthZ;
    }

    public String getUrlC() {
        return UrlC;
    }

    public void setUrlC(String urlC) {
        UrlC = urlC;
    }

    public int getHeightC() {
        return HeightC;
    }

    public void setHeightC(int heightC) {
        HeightC = heightC;
    }

    public int getWidthC() {
        return WidthC;
    }

    public void setWidthC(int widthC) {
        WidthC = widthC;
    }

    public String getUrlL() {
        return UrlL;
    }

    public void setUrlL(String urlL) {
        UrlL = urlL;
    }

    public int getHeightL() {
        return HeightL;
    }

    public void setHeightL(int heightL) {
        HeightL = heightL;
    }

    public int getWidthL() {
        return WidthL;
    }

    public void setWidthL(int widthL) {
        WidthL = widthL;
    }
}