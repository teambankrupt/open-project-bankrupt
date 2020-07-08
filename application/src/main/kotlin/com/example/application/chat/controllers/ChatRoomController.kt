package com.example.application.chat.controllers

import com.example.application.chat.models.dtos.ChatRoomDto
import com.example.application.chat.models.mappers.ChatRoomMapper
import com.example.application.chat.services.ChatRoomService
import com.example.application.commons.Constants
import com.example.application.commons.utils.ExceptionUtil
import com.example.application.domains.common.base.controllers.CrudController
import com.example.application.routing.Route
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@Api(tags = [Constants.Swagger.CHAT_ROOMS], description = Constants.Swagger.CHAT_ROOMS_DETAILS)
class ChatRoomController @Autowired constructor(
        private val chatRoomMapper: ChatRoomMapper,
        private val chatRoomService: ChatRoomService
) : CrudController<ChatRoomDto> {

    @GetMapping(Route.V1.SEARCH_CHATROOMS)
    override fun search(@RequestParam("q", defaultValue = "") query: String,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int): ResponseEntity<Page<ChatRoomDto>> {
        val chatRooms = this.chatRoomService.search(query, page, size)
        return ResponseEntity.ok(chatRooms.map { this.chatRoomMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_CHATROOM)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<ChatRoomDto> {
        val chatRoom = this.chatRoomService.find(id).orElseThrow { ExceptionUtil.getNotFound("Chat Room", id) }
        return ResponseEntity.ok(this.chatRoomMapper.map(chatRoom))
    }

    @PostMapping(Route.V1.CREATE_CHATROOM)
    override fun create(@Valid @RequestBody dto: ChatRoomDto): ResponseEntity<ChatRoomDto> {
        var chatRoom = this.chatRoomMapper.map(dto, null)
        chatRoom = this.chatRoomService.save(chatRoom)
        return ResponseEntity.ok(this.chatRoomMapper.map(chatRoom))
    }

    @PatchMapping(Route.V1.UPDATE_CHATROOM)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: ChatRoomDto): ResponseEntity<ChatRoomDto> {
        val exChatRoom = this.chatRoomService.find(id).orElseThrow { ExceptionUtil.getNotFound("Chat Room", id) }
        var chatRoom = this.chatRoomMapper.map(dto, exChatRoom)
        chatRoom = this.chatRoomService.save(chatRoom)
        return ResponseEntity.ok(this.chatRoomMapper.map(chatRoom))
    }

    @DeleteMapping(Route.V1.DELETE_CHATROOM)
    override fun delete(id: Long): ResponseEntity<Any> {
        this.chatRoomService.delete(id, true)
        return ResponseEntity.noContent().build()
    }


}