package org.wasabifx.wasabi.protocol.websocket

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.WebSocketFrame
import org.slf4j.LoggerFactory

class WebSocketFrameHandler(val handler: ChannelHandler.() -> Unit): SimpleChannelInboundHandler<WebSocketFrame>() {

    private var log = LoggerFactory.getLogger(WebSocketFrameHandler::class.java)

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: WebSocketFrame?) {

        // Init a new wrapper for the current frame.
        val response = Response()

        // Grab the handler for the current channel.
        val channelExtension : ChannelHandler.() -> Unit = handler
        val channelHandler = ChannelHandler(ctx, msg!!, response)
        channelHandler.channelExtension()
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {

        // TODO work out the best way to fire something here to notify client has disconnected.

        super.channelInactive(ctx)
    }
}