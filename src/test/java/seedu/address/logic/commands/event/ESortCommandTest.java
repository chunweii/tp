package seedu.address.logic.commands.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.general.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.event.DateAndTime.DATE_TIME_FORMATTER;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.common.Name;
import seedu.address.model.event.EndDateTime;
import seedu.address.model.event.Event;
import seedu.address.model.event.StartDateTime;

class ESortCommandTest {

    @Test
    public void execute_emptyList() {
        Model model = new ModelManager();
        ESortCommand command = new ESortCommand();
        assertCommandSuccess(command, model, ESortCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyList() {
        UserPrefs userPrefs = new UserPrefs();
        AddressBook addressBook = new AddressBook();
        LocalDateTime timeNow = LocalDateTime.now();
        String beforeNow1 = timeNow.minusDays(10).format(DATE_TIME_FORMATTER);
        String beforeNow2 = timeNow.minusHours(3).minusDays(1).format(DATE_TIME_FORMATTER);
        String starting = timeNow.format(DATE_TIME_FORMATTER);
        String after1 = timeNow.plusDays(7).plusHours(4).plusMinutes(30).format(DATE_TIME_FORMATTER);
        String after2 = timeNow.plusYears(1).format(DATE_TIME_FORMATTER);
        String after3 = timeNow.plusYears(1).format(DATE_TIME_FORMATTER);
        Event[] events = new Event[]{new Event(new Name("Event 1"), new StartDateTime(after2), null, null,
            null, null, Set.of()),
            new Event(new Name("Event 2"), new StartDateTime(beforeNow2), new EndDateTime(starting), null,
            null, null, Set.of()),
            new Event(new Name("Event 3"), new StartDateTime(after3), null, null,
            null, null, Set.of()),
            new Event(new Name("Event 4"), new StartDateTime(starting), null, null,
            null, null, Set.of()),
            new Event(new Name("Event 5"), new StartDateTime(beforeNow1), new EndDateTime(after2), null,
            null, null, Set.of()),
            new Event(new Name("Event 6"), new StartDateTime(beforeNow1), new EndDateTime(after1), null,
            null, null, Set.of()),
            new Event(new Name("Event 7"), new StartDateTime(beforeNow1), new EndDateTime(beforeNow2), null,
            null, null, Set.of()),
            new Event(new Name("Event 8"), new StartDateTime(beforeNow2), null, null,
            null, null, Set.of())};
        Model model = new ModelManager(addressBook, userPrefs);
        for (Event e : events) {
            model.addEvent(e);
        }
        ESortCommand command = new ESortCommand();
        List<Event> expectedList = List.of(events[4], events[5], events[0], events[2]);
        command.execute(model);
        assertEquals(model.getFilteredEventList(), expectedList);
    }
}
