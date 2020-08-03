package br.com.sga.web.constants;

public class ControllerMapping{
	
	// Used as welcome file (path)
	public static final String WELCOME_PATH_ROOT						= 	"/";
	
	public static final String FORM_SUCCESS_MESSAGE_KEY 				= 	"successMessage";
	public static final String FORM_ERROR_MESSAGE_KEY					=	"errorMessage";
	public static final String FORM_INFO_MESSAGE_KEY					=	"infoMessage";
	public static final String FORM_WARN_MESSAGE_KEY					=	"warnMessage";
	
	public static final String PATH_ACTION_NEW							= 	"/form";
	public static final String PATH_ACTION_EDIT							= 	"/form/{id}";
	public static final String PATH_ACTION_SAVE							= 	"/save";
	public static final String PATH_ACTION_DELETE						= 	"/delete/{id}";
	
	public static final String MODEL_ERROR_DETAILS_NAME 				= 	"errorDetails";
	
	public static final String DASHBOARD_PATH_ROOT						= 	"/dashboard";
	public static final String DASHBOARD_VIEW							= 	"index";
	public static final String DASHBOARD_MODEL_DTO						= 	"dashboardDto";
	
	public static final String AUTH_PATH_ROOT							= 	"/auth";
	public static final String AUTH_PATH_LOGIN 							= 	"/login";
	public static final String AUTH_PATH_LOGIN_FORM						= 	"/login-form";
	public static final String AUTH_PATH_LOGIN_ERROR					= 	"/login/error";
	public static final String AUTH_PATH_LOGOUT_SUCCESS					= 	"/logout/success";
	public static final String AUTH_PATH_LOGOUT_CONCURRENCY				= 	"/logout/concurrency";
	public static final String AUTH_PATH_DENIED 						= 	"/denied";
	public static final String AUTH_VIEW_LOGIN 							= 	"core-auth/login";
	public static final String AUTH_VIEW_LOGIN_FORM						= 	"core-auth/login-form";
	public static final String AUTH_VIEW_LOGOUT_SUCCESS					= 	"core-auth/logout-success";
	public static final String AUTH_VIEW_LOGOUT_CONCURRENCY				= 	"core-auth/logout-concurrency";
	public static final String AUTH_VIEW_DENIED							= 	"core-auth/access-denied";
	public static final String AUTH_MODEL_LOGIN_ERROR_DETAILS_NAME 		= 	"loginErrorDetails";
	
	public static final String ERRORS_PATH_ROOT							= 	"/errors";
	public static final String ERRORS_PATH_404							= 	"/404";
	public static final String ERRORS_PATH_405							= 	"/405";
	public static final String ERRORS_PATH_500							= 	"/500";
	public static final String ERRORS_VIEW_PAGE_NOT_FOUND				= 	"core-errors/page-not-found";
	public static final String ERRORS_VIEW_METHOD_NOT_SUPPORTED			= 	"core-errors/method-not-supported";
	public static final String ERRORS_VIEW_INTERNAL_ERROR				= 	"core-errors/internal-error";
	
	public static final String USER_PATH_ROOT							= 	"/user";
	public static final String USER_PATH_PROFILE						= 	"/profile";
	public static final String USER_VIEW_PROFILE						= 	"user/profile-info";
	public static final String USER_MODEL_USUARIO_AUTENTICADO_NAME 		= 	"usuarioAutenticado";
	public static final String USER_MODEL_USUARIO_AUTENTICADO_DETAILS	= 	"usuarioAutenticadoDetails";
	
	public static final String SETTINGS_PATH_ROOT						=	"/settings";
	public static final String SETTINGS_PATH_INFORMATION				=	"/information";
	public static final String SETTINGS_VIEW_INFORMATION				=	"settings/setting";
	
	public static final String COMPONENTS_PATH_ROOT						=	"/components";
	public static final String COMPONENTS_VIEW_LIST 					=	"component/component-list";
	
}