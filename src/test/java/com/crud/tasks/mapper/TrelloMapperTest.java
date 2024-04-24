package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class TrelloMapperTest {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToBoards() {
        //Given
        TrelloBoardDto trelloBoardDto =
                new TrelloBoardDto("id 1", "Board 1", List.of(new TrelloListDto("id 1", "List 1", false)));
        List<TrelloBoardDto> boardDtoList = List.of(trelloBoardDto);

        //When
        List<TrelloBoard> boards = trelloMapper.mapToBoards(boardDtoList);

        //Then
        assertEquals(1, boards.size());
        assertEquals("id 1", boards.get(0).getId());
        assertEquals("Board 1", boards.get(0).getName());
        assertEquals(1, boards.get(0).getLists().size());
        assertEquals("id 1", boards.get(0).getLists().get(0).getId());
        assertEquals("List 1", boards.get(0).getLists().get(0).getName());
        assertFalse(boards.get(0).getLists().get(0).isClosed());
    }

    @Test
    void shouldMapToBoardsDto() {
        //Given
        TrelloBoard trelloBoard =
                new TrelloBoard("id 1", "Board 1", List.of(new TrelloList("id 1", "List 1", false)));
        List<TrelloBoard> boards = List.of(trelloBoard);

        //When
        List<TrelloBoardDto> boardDtoList = trelloMapper.mapToBoardsDto(boards);

        //Then
        assertEquals(1, boardDtoList.size());
        assertEquals("id 1", boardDtoList.get(0).getId());
        assertEquals("Board 1", boardDtoList.get(0).getName());
        assertEquals(1, boardDtoList.get(0).getLists().size());
        assertEquals("id 1", boardDtoList.get(0).getLists().get(0).getId());
        assertEquals("List 1", boardDtoList.get(0).getLists().get(0).getName());
        assertFalse(boardDtoList.get(0).getLists().get(0).isClosed());
    }

    @Test
    void shouldMapToList() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("id 1", "List 1", false);

        //When
        TrelloList list = trelloMapper.mapToList(List.of(trelloListDto)).get(0);

        //Then
        assertEquals("id 1", list.getId());
        assertEquals("List 1", list.getName());
        assertFalse(list.isClosed());
    }

    @Test
    void shouldMapToListDto() {
        //Given
        TrelloList trelloList = new TrelloList("id 1", "List 1", false);

        //When
        TrelloListDto listDto = trelloMapper.mapToListDto(List.of(trelloList)).get(0);

        //Then
        assertEquals("id 1", listDto.getId());
        assertEquals("List 1", listDto.getName());
        assertFalse(listDto.isClosed());
    }

    @Test
    void shouldMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card 1", "Description 1", "top", "id 1");

        //When
        TrelloCard card = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("Card 1", card.getName());
        assertEquals("Description 1", card.getDescription());
        assertEquals("top", card.getPos());
        assertEquals("id 1", card.getListId());
    }

    @Test
    void shouldMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("Card 1", "Description 1", "top", "id 1");

        //When
        TrelloCardDto cardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("Card 1", cardDto.getName());
        assertEquals("Description 1", cardDto.getDescription());
        assertEquals("top", cardDto.getPos());
        assertEquals("id 1", cardDto.getListId());
    }

}