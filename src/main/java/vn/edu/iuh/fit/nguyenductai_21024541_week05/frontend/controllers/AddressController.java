package vn.edu.iuh.fit.nguyenductai_21024541_week05.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.exceptions.EntityIdNotFoundException;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models.Address;
import vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.services.impl.AddressService;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // Endpoint to add a single address
    @PostMapping("/add")
    public String addAddress(@ModelAttribute Address address, Model model) {
        Address savedAddress = addressService.add(address);
        model.addAttribute("address", savedAddress);
        model.addAttribute("message", "Address added successfully");
        return "address/add_address"; // View to display address details
    }

    // Endpoint to add multiple addresses
    @PostMapping("/addAll")
    public String addAddresses(@ModelAttribute List<Address> addresses, Model model) {
        List<Address> savedAddresses = addressService.addMany(addresses);
        model.addAttribute("addresses", savedAddresses);
        model.addAttribute("message", "Addresses added successfully");
        return "address/addresses_list"; // View to display all addresses
    }

    // Endpoint to update an address
    @PutMapping("/update")
    public String updateAddress(@RequestParam Long id, @ModelAttribute Address address, Model model) {
        try {
            address.setId(id);
            Address updatedAddress = addressService.update(address);
            model.addAttribute("address", updatedAddress);
            model.addAttribute("message", "Address updated successfully");
        } catch (EntityIdNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "address/address_details"; // View to display updated address
    }

    // Endpoint to delete an address
    @DeleteMapping("/delete")
    public String deleteAddress(@RequestParam Long id, Model model) {
        try {
            addressService.delete(id);
            model.addAttribute("message", "Address deleted successfully");
        } catch (EntityIdNotFoundException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "address/address_response"; // View to display deletion result
    }

    // Endpoint to get an address by ID
    @GetMapping("/getById")
    public String getAddressById(@RequestParam Long id, Model model) {
        try {
            Optional<Address> address = addressService.getById(id);
            model.addAttribute("address", address.orElse(null));
            return "address/address_details"; // View to display address details
        } catch (EntityIdNotFoundException e) {
            model.addAttribute("message", e.getMessage());
            return "address/address_response"; // View to display error message
        }
    }

    // Endpoint to get all addresses
    @GetMapping("/getAll")
    public String getAllAddresses(Model model) {
        Iterator<Address> addresses = addressService.getAll();
        model.addAttribute("addresses", addresses);
        return "address/addresses_list"; // View to show all addresses
    }

    // Endpoint to search addresses by city
    @GetMapping("/searchByCity")
    public String getAddressesByCity(@RequestParam String city, Model model) {
        List<Address> addresses = addressService.findByCity(city);
        model.addAttribute("addresses", addresses);
        return "address/addresses_list"; // View to show addresses matching the city
    }

    // Endpoint to search addresses by country
    @GetMapping("/searchByCountry")
    public String getAddressesByCountry(@RequestParam String country, Model model) {
        List<Address> addresses = addressService.findByCountry(country);
        model.addAttribute("addresses", addresses);
        return "address/addresses_list"; // View to show addresses matching the country
    }

    // Endpoint to search addresses by street
    @GetMapping("/searchByStreet")
    public String getAddressesByStreet(@RequestParam String street, Model model) {
        List<Address> addresses = addressService.findByStreet(street);
        model.addAttribute("addresses", addresses);
        return "address/addresses_list"; // View to show addresses matching the street
    }
}
