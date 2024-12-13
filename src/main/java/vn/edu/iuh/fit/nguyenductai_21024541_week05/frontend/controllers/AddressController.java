package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Response;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.AddressModel;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    private final AddressModel addressModel;

    @Autowired
    public AddressController(AddressModel addressModel) {
        this.addressModel = addressModel;
    }

    // Phương thức thêm một Address
    @GetMapping("/insert-address")
    public String insertAddress(Address address, Model model) {
        Response response = addressModel.insert(address);
        model.addAttribute("response", response);
        return "address_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức thêm nhiều Address
    @GetMapping("/insertAll-address")
    public String insertAllAddresses(@RequestParam List<Address> addresses, Model model) {
        Response response = addressModel.insertAll(addresses);
        model.addAttribute("response", response);
        return "address_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức cập nhật một Address
    @GetMapping("/update-address")
    public String updateAddress(@RequestParam Long id, Address address, Model model) {
        Response response = addressModel.update(id, address);
        model.addAttribute("response", response);
        return "address_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức xóa một Address
    @GetMapping("/delete-address")
    public String deleteAddress(@RequestParam Long id, Model model) {
        Response response = addressModel.delete(id);
        model.addAttribute("message", "Address deleted successfully");
        return "address_response"; // View để hiển thị thông báo kết quả
    }

    // Phương thức lấy Address theo ID
    @GetMapping("/getById-address")
    public String getAddressById(@RequestParam Long id, Model model) {
        Optional<Address> address = addressModel.getById(id);
        model.addAttribute("address", address.orElse(null)); // Returns null if not present
        return "address_details"; // View để hiển thị thông tin Address
    }

    // Phương thức lấy tất cả các Address
    @GetMapping("/getAll-address")
    public String getAllAddresses(Model model) {
        List<Address> addresses = addressModel.getAll();
        model.addAttribute("addresses", addresses);
        return "addresses_list"; // View để hiển thị danh sách Address
    }

    // Tìm kiếm Address theo thành phố
    @GetMapping("/findByCity-address")
    public String findByCity(@RequestParam String city, Model model) {
        List<Address> addresses = addressModel.findByCity(city);
        model.addAttribute("addresses", addresses);
        return "addresses_list"; // Hiển thị danh sách Address theo thành phố
    }

    // Tìm kiếm Address theo quốc gia
    @GetMapping("/findByCountry-address")
    public String findByCountry(@RequestParam String country, Model model) {
        List<Address> addresses = addressModel.findByCountry(country);
        model.addAttribute("addresses", addresses);
        return "addresses_list"; // Hiển thị danh sách Address theo quốc gia
    }

    // Tìm kiếm Address theo đường phố
    @GetMapping("/findByStreet-address")
    public String findByStreet(@RequestParam String street, Model model) {
        List<Address> addresses = addressModel.findByStreet(street);
        model.addAttribute("addresses", addresses);
        return "addresses_list"; // Hiển thị danh sách Address theo đường phố
    }
}
