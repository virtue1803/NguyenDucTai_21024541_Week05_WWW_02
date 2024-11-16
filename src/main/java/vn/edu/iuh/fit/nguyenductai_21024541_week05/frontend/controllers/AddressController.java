package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.models.AddressModel;

import java.util.List;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressModel addressModel;

    @GetMapping
    public ModelAndView listAllAddresses() {
        List<Address> addresses = addressModel.getAll();
        ModelAndView mav = new ModelAndView("address-list");
        mav.addObject("addresses", addresses);
        return mav;
    }

    @GetMapping("/{id}")
    public ModelAndView getAddressById(@PathVariable("id") Long id) {
        Address address = addressModel.getById(id);
        ModelAndView mav = new ModelAndView("address-detail");
        mav.addObject("address", address);
        return mav;
    }

    @GetMapping("/create")
    public ModelAndView showCreateAddressForm() {
        ModelAndView mav = new ModelAndView("address-create");
        mav.addObject("address", new Address());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView createAddress(@ModelAttribute Address address) {
        addressModel.insert(address);
        return new ModelAndView("redirect:/addresses");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditAddressForm(@PathVariable("id") Long id) {
        Address address = addressModel.getById(id);
        ModelAndView mav = new ModelAndView("address-edit");
        mav.addObject("address", address);
        return mav;
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editAddress(@PathVariable("id") Long id, @ModelAttribute Address address) {
        addressModel.update(id, address);
        return new ModelAndView("redirect:/addresses/" + id);
    }

    @PostMapping("/{id}/delete")
    public ModelAndView deleteAddress(@PathVariable("id") Long id) {
        addressModel.delete(id);
        return new ModelAndView("redirect:/addresses");
    }
}
