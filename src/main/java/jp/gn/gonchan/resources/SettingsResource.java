package jp.gn.gonchan.resources;

import jp.gn.gonchan.constant.DisplayMessageLevel;
import jp.gn.gonchan.dto.cokkie.CokkieArticleDto;
import jp.gn.gonchan.dto.cokkie.CokkieInfo;
import jp.gn.gonchan.dto.display.AbstractDisplayDto;
import jp.gn.gonchan.dto.display.SettingsDisplayDto;
import jp.gn.gonchan.form.RegisterSettingsForm;
import jp.gn.gonchan.logics.CokkieLogic;
import jp.gn.gonchan.logics.HtmlTemplateLogic;
import jp.gn.gonchan.util.CommonValidator;
import jp.gn.gonchan.util.DisplayMessage;

import io.dropwizard.jersey.caching.CacheControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/settings")
@Singleton
public class SettingsResource extends AbstractResource {

    private static final Logger logger = LoggerFactory.getLogger(SettingsResource.class);

    @Inject
    HtmlTemplateLogic templateLogic;

    @Inject
    CokkieLogic cokkieLogic;

    /**
     * トップ画面表示に必要な情報を取得し、トップ画面を表示する.
     */
    @GET
    @CacheControl(noCache = true, noStore = true)
    public Response displaySettingsScreen(@Context CokkieInfo cokkieInfo) {
        AbstractDisplayDto displayDto = new AbstractDisplayDto() {

            @Override
            public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append("AbstractDisplayDto [getNavDto=");
                builder.append(super.getNavDto());
                builder.append("]");
                return builder.toString();
            }
        };
        if (cokkieInfo != null) {
            displayDto = fetchCookieInfo(cokkieInfo);
        }
        setDisplayDto(displayDto, cokkieInfo);
        return Response.ok(templateLogic.getHtml("template/html/settings.html", displayDto)).build();
    }

    private AbstractDisplayDto fetchCookieInfo(CokkieInfo cokkieInfo) {
        SettingsDisplayDto displayDto = new SettingsDisplayDto();
        displayDto.setTargetSelf(cokkieInfo.isTargetSelf());
        logger.debug(displayDto.toString());
        return displayDto;
    }

    @POST
    public Response registerSettingsToCokkie(@Context CokkieInfo cokkieInfo, @BeanParam RegisterSettingsForm form) {

        CommonValidator<RegisterSettingsForm> validator = new CommonValidator<>();
        List<String> errorMessages = validator.validate(form);
        if (errorMessages.size() > 0) {
            DisplayMessage dm = new DisplayMessage(errorMessages, DisplayMessageLevel.ERROR);
            // TODO エラー画面に出力したい
            return Response.ok(templateLogic.getHtml("template/html/top.html", dm)).build();
        }

        final boolean targetSelf = Boolean.valueOf(form.getTargetSelf());
        NewCookie cookies;
        if (cokkieInfo == null) {
            cookies = cokkieLogic.createCookie(new CokkieInfo(targetSelf));
        } else {
            List<CokkieArticleDto> cokkieArticleList = cokkieInfo.getCokkieArticleDto();
            cookies = cokkieLogic.createCookie(new CokkieInfo(cokkieArticleList, targetSelf));
        }
        return Response.seeOther(URI.create("/")).cookie(cookies).build();
    }
}
