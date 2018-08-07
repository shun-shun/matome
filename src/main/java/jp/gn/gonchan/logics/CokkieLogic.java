package jp.gn.gonchan.logics;

import jp.gn.gonchan.constant.Constant;
import jp.gn.gonchan.dto.cokkie.CokkieInfo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.util.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;

public class CokkieLogic {

    private static final Logger logger = LoggerFactory.getLogger(CokkieLogic.class);

    private static final int RESPONSE_HEADER_COOKIE_MAX_AGE = 60 * 60 * 24 * 7;

    /**
     * Return cookie of login information.
     */
    public NewCookie createCookie(final CokkieInfo loginInfo) {
        byte[] binary = serializeCokkieInfo(loginInfo);
        Base64.Encoder encoder = Base64.getMimeEncoder(0, ArrayUtils.EMPTY_BYTE_ARRAY);
        String cookieStr = encoder.encodeToString(binary);
        Cookie cookie = new Cookie(Constant.COOKIE_NAME, cookieStr, "/", null);
        return new NewCookie(cookie, null, RESPONSE_HEADER_COOKIE_MAX_AGE, null, false, false);
    }

    /**
     * Return decoded {@link CokkieInfo} instance.
     */
    public CokkieInfo createCokkieInfoFromCookie(final Cookie cookie) {
        Base64.Decoder decoder = Base64.getMimeDecoder();
        byte[] binary = decoder.decode(cookie.getValue());
        return deserializeCokkieInfo(binary);
    }

    @VisibleForTesting
    byte[] serializeCokkieInfo(CokkieInfo loginInfo) {
        Kryo kryo = createKryoInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (Output output = new Output(baos)) {
            kryo.writeObject(output, loginInfo);
        }
        return baos.toByteArray();
    }

    @VisibleForTesting
    CokkieInfo deserializeCokkieInfo(byte[] binary) {
        Kryo kryo = createKryoInstance();
        try (Input input = new Input(new ByteArrayInputStream(binary))) {
            CokkieInfo loginInfo = kryo.readObject(input, CokkieInfo.class);
            return loginInfo;
        }
    }

    @VisibleForTesting
    Kryo createKryoInstance() {
        final Kryo instance = new Kryo();
        final FieldSerializer<CokkieInfo> serializer = new FieldSerializer<>(instance, CokkieInfo.class);
        serializer.setFieldsCanBeNull(false);
        instance.register(CokkieInfo.class, serializer);
        return instance;
    }

}
