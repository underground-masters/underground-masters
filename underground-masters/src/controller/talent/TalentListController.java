package controller.talent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.talent.TalentDAO;
import model.talent.TalentDTO;
import util.AuthenticationSession;

import java.io.IOException;
import java.util.List;

import controller.common.NavbarController;
import javafx.beans.property.SimpleStringProperty;

public class TalentListController extends NavbarController {

	@FXML
	private TableView<TalentDTO> TalentTableView;

	@FXML
	private TableColumn<TalentDTO, String> talentName;

	@FXML
	private TableColumn<TalentDTO, String> memberName;

	private final TalentDAO talentDAO = new TalentDAO();

	@FXML
	public void initialize() {
		// 테이블 컬럼에 DTO의 속성 연결
		talentName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		memberName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt()));

		// 데이터 로드
		loadTalentList();

		// 더블 클릭 이벤트
		TalentTableView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				TalentDTO selectedTalent = TalentTableView.getSelectionModel().getSelectedItem();
				if (selectedTalent != null) {
					openTalentDetailPopup(selectedTalent);
				}
			}
		});
	}

	public void loadTalentList() {
		int memberId = AuthenticationSession.getInstance().getMember().getMemberId();
		List<TalentDTO> talents = talentDAO.findTalentListByMemberId(memberId);
		ObservableList<TalentDTO> talentList = FXCollections.observableArrayList(talents);
		TalentTableView.setItems(talentList);
	}

	@FXML
	private void onAddTalentClicked(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/talent/CreateTalentPopup.fxml"));
			Parent root = loader.load();

			CreateTalentPopupController popupController = loader.getController();
			popupController.setTalentListController(this);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("재능 등록");
			stage.setScene(new Scene(root));
			stage.showAndWait();

			loadTalentList(); // 등록 후 갱신
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openTalentDetailPopup(TalentDTO talent) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/talent/TalentDetailPopup.fxml"));
			Parent root = loader.load();

			TalentDetailPopupController controller = loader.getController();
			controller.setTalent(talent);
			controller.setTalentListController(this);

			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("재능 상세");
			stage.setScene(new Scene(root));
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}