package com.amazon.dartdisbursementpipelinestoollambda.activity

import com.amazon.bones.lambdarouter.LambdaRouter
import com.amazon.bones.lambdarouter.LambdaRouterSignature
import com.amazon.bones.lambdarouter.handlers.*

import com.amazon.bones.lambdarouter.LambdaRouterHttpMethod.GET
import com.amazon.dartdisbursementpipelinestoollambda.activity.handlers.CORSPreflightHandler
import com.amazon.dartdisbursementpipelinestoollambda.activity.handlers.MidwayRequestHandler
import com.amazon.dartdisbursementpipelinestoollambda.activity.handlers.MidwaySSOLoginHandler
import com.amazon.dartdisbursementpipelinestoollambda.activity.handlers.RequestOriginCORSHandler

/**
 * Router class is instantiate only once within same JVM and so it is reused by subsequent calls. This class is
 * a good place to keep references to resources that are expensive to create.
 */
class DartDisbursementPipelinesToolLambdaRouter : LambdaRouter {



    /**
     * Default constructor.
     */
    constructor() {}

    /**
     * The main thing this methods is doing to is to initialize the request processing chain.
     *
     *
     * **Important note:** for each request a new instance of LambdaActivity is created. This is similar to Coral
     * handles activities.
     *
     * To store expensive to create resources between calls use fields from this class and inject them
     * at creation time.
     *
     *
     * It is possible to use a memoization supplier if due to applicaiton specific reason is necessary to have
     * the same LambdaActivity instance to handle all the request.
     *
     */
    private val whiteListOrigins = listOf("*aka.amazom.com", "localhost:5000")

    override fun initialize() {

        val dataConverter = dataConverter

        chain.setHandlers(
                // Keep it after ResultSerializer to swallow the exceptions thrown by Xray.
                XRayTraceHandler("DartDisbursementPipelinesToolLambda"),
                ProxyResultSerializerHandler(dataConverter),

                RequestOriginCORSHandler(),
                CORSPreflightHandler(),
                MidwayRequestHandler(dataConverter),

                RouteMatcherHandler()
                        .withRoute(LambdaRouterSignature(GET, "/filters"),
                                { GetFiltersActivity() } )
                        .withRoute(LambdaRouterSignature(GET,
                                "/pipelines/{domain}/{pageNumber}"),
                                { GetPipelinesActivity()}),

                ActivityInstantiatorHandler(),
                RequestDeserializerHandler(dataConverter),

                // Optional - uncomment if you want "null" bodies transformed into "empty" classes of the target type.
                // new NonNullRequestTransformHandler(dataConverter),
                ActivityInvocationHandler())
    }
}
