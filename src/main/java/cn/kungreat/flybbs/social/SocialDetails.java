package cn.kungreat.flybbs.social;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocialDetails {
    private String providerId;
    private String providerSubscriberId;
    private String nickname;
    private String imgPath;
}
