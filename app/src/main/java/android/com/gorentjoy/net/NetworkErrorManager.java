package android.com.gorentjoy.net;

import android.app.AlertDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.util.Util;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 */
public class NetworkErrorManager {

    public enum ErrorCodeType {
        VOLLEY, LOGIN, REGISTER, CONFIRM
    }

    public enum ErrorCode {
        //Volley
        TIME_OUT_ERROR(10001, R.string.error_time_out, ErrorCodeType.VOLLEY),
        NO_CONNECTION_ERROR(10002, R.string.error_no_network, ErrorCodeType.VOLLEY),
        INTERNAL_SERVER_ERROR(10004, R.string.error_server, ErrorCodeType.VOLLEY),
        CERTIFICATE_INVALID(10007, R.string.error_server_failure, ErrorCodeType.VOLLEY),
        NETWORK_ERROR(10005, R.string.error_network, ErrorCodeType.VOLLEY),
        WRONG_KEY_ERROR(401, R.string.wrong_api_key, ErrorCodeType.VOLLEY),
        PARSE_ERROR(10006, R.string.error_parse, ErrorCodeType.VOLLEY),
        SERVER_FAILED(1, R.string.error_server_failure, ErrorCodeType.VOLLEY),
        INVALID_API_KEY(54, R.string.error_server_failure, ErrorCodeType.VOLLEY),
        INVALID_JSON(72, R.string.error_server_failure, ErrorCodeType.VOLLEY),
        INVALID_PAYLOAD(60, R.string.error_server_failure, ErrorCodeType.VOLLEY),
        BAD_REQUEST(74, R.string.error_server_failure, ErrorCodeType.VOLLEY),
        SERVER_TEMPORARILY_UNAVAILABLE(93, R.string.error_server_failure, ErrorCodeType.VOLLEY),

        //LOGIN
        INCORRECT_USERNAME_PASSWORD(401, R.string.error_incorrect_password, ErrorCodeType.LOGIN),
        AUTH_MISSING(50, R.string.error_unidentified_server_error, ErrorCodeType.LOGIN),

        //REGISTER
        USER_EXIST(500, R.string.error_user_exist, ErrorCodeType.REGISTER),

        //LOGOUT ON FOLLOWING SCENARIOS
        ACCOUNT_NOT_FOUND(52, R.string.error_account_not_found, ErrorCodeType.LOGIN),
        REFRESH_TOKEN_EXPIRED(53, R.string.error_server_failure, ErrorCodeType.LOGIN),
        CONFIRM_LOGOUT(70, R.string.confirm_logout, ErrorCodeType.CONFIRM);

        private final int errorCode;

        private final int stringId;

        private final ErrorCodeType errorCodeType;

        private static final Map<Integer, ErrorCode> map = new HashMap<>();

        static {
            for (ErrorCode codesEnum : ErrorCode.values()) {
                map.put(codesEnum.errorCode, codesEnum);
            }
        }

        ErrorCode(int code, int id, ErrorCodeType type) {
            errorCode = code;
            stringId = id;
            errorCodeType = type;
        }

        public static ErrorCode valueOf(int errorCode) {
            return map.get(errorCode);
        }

        public int getErrorCode() {
            return errorCode;
        }

        public int getStringId() {
            return stringId;
        }

        public ErrorCodeType getErrorCodeType() {
            return errorCodeType;
        }
    }

    public static void showErrors(Context context, int errorId, AlertDialog.Builder alert) {
        ErrorCode errorCode = ErrorCode.valueOf(errorId);
        if (errorCode != null) {
            if (errorCode.getErrorCodeType() == ErrorCodeType.VOLLEY) {
                showVolleyErrors(context, errorCode);
            } else if (errorCode.getErrorCodeType() == ErrorCodeType.LOGIN) {
                showLoginErrors(context, errorCode, alert);
            } else if(errorCode.getErrorCodeType() == ErrorCodeType.REGISTER) {
                showLoginErrors(context, errorCode, alert);
            } else if(errorCode.getErrorCodeType() == ErrorCodeType.CONFIRM) {

            }
        } else {
            DialogInterface.OnClickListener okClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            };
            showErrorInAlert(context, R.string.error_server_failure, okClickListener);
        }
    }

    private static AlertDialog networkAlert;

    private static void showVolleyErrors(Context context, ErrorCode errorCode) {

        if (networkAlert == null || !networkAlert.isShowing()) {
            AlertDialog.Builder builder = Util.createAlert(context,
                    null,
                    null,
                    context.getString(errorCode.getStringId()),
                    R.string.button_ok,
                    0,
                    0,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    },
                    null,
                    null);

            networkAlert = builder.create();
            networkAlert.show();
        }
    }

    private static void showLoginErrors(Context context, ErrorCode errorCode, AlertDialog.Builder alertBuilder) {
        if (alertBuilder != null) {
            alertBuilder.setMessage(errorCode.getStringId());
            AlertDialog dialog = alertBuilder.show();
            Util.styleShownAlert(context, dialog);
        }
    }

    private static void showError(Context context, int errorMessageResourceId) {
        //showError(context, context.getString(errorMessageResourceId));
        Toast.makeText(context, context.getString(errorMessageResourceId), Toast.LENGTH_SHORT).show();
    }

    private static void showErrorInAlert(Context context, int errorMessageResourceId, DialogInterface.OnClickListener onClickListener) {

        Util.createAndShowAlert(context,
                null,
                null,
                context.getString(errorMessageResourceId),
                R.string.button_ok,
                0,
                0,
                onClickListener,
                null,
                null);
    }


}
