package controller.matching;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import controller.common.NavbarController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.matching.*;
import util.*;

public class MatchingSendController extends NavbarController implements Initializable {

    @FXML private TableView<MatchingDTO> MatchingTableView;
    @FXML private TableColumn<MatchingDTO, String> talentName;
    @FXML private TableColumn<MatchingDTO, String> memberName;
    @FXML private TableColumn<MatchingDTO, String> status;
    @FXML private TableColumn<MatchingDTO, String> requestDate;

    private final MatchingDAO dao = new MatchingDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        talentName.setCellValueFactory(new PropertyValueFactory<>("requestedTalent"));
        memberName.setCellValueFactory(new PropertyValueFactory<>("requesterName"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        requestDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));

        loadMatchingList();
    }

    public void loadMatchingList() {
        int memberId = AuthenticationSession.getInstance().getMember().getMemberId();

        try {
            List<MatchingDTO> result = dao.findSendMatchingList(memberId);
            ObservableList<MatchingDTO> list = FXCollections.observableArrayList(result);
            MatchingTableView.setItems(list);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onShowDetail() {
        // 나중에 상세 팝업 기능 구현 예정
    }
}