package au.com.contractser.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Blob;

public class Contract implements Parcelable {
    private int idContract;
    private String fullName;
    private String passportNumber;
    private String passportCountry;
    private String dateBirth;
    private String sexo;
    private String address;
    private String suburb;
    private String state;
    private String postcode;
    private String email;
    private String school;
    private String visaAgency;
    private String australianNumber;
    private String whatsApp;
    private String serialNumber;
    private String model;
    private String color;
    private String numberWeeks;
    private String weeklyRate;
    private String startDate;
    private String returnDate;
    private String pmtDay;
    private String firstWeek;
    private String secDep;
    private String accessoriesIncluded;
    private String notes;
    private int ativo;
    private String fechaData;
    private String passportPhoto;
    private String assinaturaPhoto;
    private String userName;
    private int termo1;
    private int termo2;
    private int termo3;

    public Contract() {
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPassportCountry() {
        return passportCountry;
    }

    public void setPassportCountry(String passportCountry) {
        this.passportCountry = passportCountry;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getVisaAgency() {
        return visaAgency;
    }

    public void setVisaAgency(String visaAgency) {
        this.visaAgency = visaAgency;
    }

    public String getAustralianNumber() {
        return australianNumber;
    }

    public void setAustralianNumber(String australianNumber) {
        this.australianNumber = australianNumber;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNumberWeeks() {
        return numberWeeks;
    }

    public void setNumberWeeks(String numberWeeks) {
        this.numberWeeks = numberWeeks;
    }

    public String getWeeklyRate() {
        return weeklyRate;
    }

    public void setWeeklyRate(String weeklyRate) {
        this.weeklyRate = weeklyRate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getPmtDay() {
        return pmtDay;
    }

    public void setPmtDay(String pmtDay) {
        this.pmtDay = pmtDay;
    }

    public String getFirstWeek() {
        return firstWeek;
    }

    public void setFirstWeek(String firstWeek) {
        this.firstWeek = firstWeek;
    }

    public String getSecDep() {
        return secDep;
    }

    public void setSecDep(String secDep) {
        this.secDep = secDep;
    }

    public String getAccessoriesIncluded() {
        return accessoriesIncluded;
    }

    public void setAccessoriesIncluded(String accessoriesIncluded) {
        this.accessoriesIncluded = accessoriesIncluded;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPassportPhoto() {
        return passportPhoto;
    }

    public void setPassportPhoto(String passportPhoto) {
        this.passportPhoto = passportPhoto;
    }

    public String getAssinaturaPhoto() {
        return assinaturaPhoto;
    }

    public void setAssinaturaPhoto(String assinaturaPhoto) {
        this.assinaturaPhoto = assinaturaPhoto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTermo1() {
        return termo1;
    }

    public void setTermo1(int termo1) {
        this.termo1 = termo1;
    }

    public int getTermo2() {
        return termo2;
    }

    public void setTermo2(int termo2) {
        this.termo2 = termo2;
    }

    public int getTermo3() {
        return termo3;
    }

    public void setTermo3(int termo3) {
        this.termo3 = termo3;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public String getFechaData() {
        return fechaData;
    }

    public void setFechaData(String fechaData) {
        this.fechaData = fechaData;
    }


    protected Contract(Parcel in) {
        idContract = in.readInt();
        fullName = in.readString();
        passportNumber = in.readString();
        passportCountry = in.readString();
        dateBirth = in.readString();
        sexo = in.readString();
        address = in.readString();
        suburb = in.readString();
        state = in.readString();
        postcode = in.readString();
        email = in.readString();
        school = in.readString();
        visaAgency = in.readString();
        australianNumber = in.readString();
        whatsApp = in.readString();
        serialNumber = in.readString();
        model = in.readString();
        color = in.readString();
        numberWeeks = in.readString();
        weeklyRate = in.readString();
        startDate = in.readString();
        returnDate = in.readString();
        pmtDay = in.readString();
        firstWeek = in.readString();
        secDep = in.readString();
        accessoriesIncluded = in.readString();
        notes = in.readString();
        ativo = in.readInt();
        fechaData = in.readString();
        passportPhoto = in.readString();
        assinaturaPhoto = in.readString();
        userName = in.readString();
        termo1 = in.readInt();
        termo2 = in.readInt();
        termo3 = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idContract);
        dest.writeString(fullName);
        dest.writeString(passportNumber);
        dest.writeString(passportCountry);
        dest.writeString(dateBirth);
        dest.writeString(sexo);
        dest.writeString(address);
        dest.writeString(suburb);
        dest.writeString(state);
        dest.writeString(postcode);
        dest.writeString(email);
        dest.writeString(school);
        dest.writeString(visaAgency);
        dest.writeString(australianNumber);
        dest.writeString(whatsApp);
        dest.writeString(serialNumber);
        dest.writeString(model);
        dest.writeString(color);
        dest.writeString(numberWeeks);
        dest.writeString(weeklyRate);
        dest.writeString(startDate);
        dest.writeString(returnDate);
        dest.writeString(pmtDay);
        dest.writeString(firstWeek);
        dest.writeString(secDep);
        dest.writeString(accessoriesIncluded);
        dest.writeString(notes);
        dest.writeInt(ativo);
        dest.writeString(fechaData);
        dest.writeString(passportPhoto);
        dest.writeString(assinaturaPhoto);
        dest.writeString(userName);
        dest.writeInt(termo1);
        dest.writeInt(termo2);
        dest.writeInt(termo3);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contract> CREATOR = new Parcelable.Creator<Contract>() {
        @Override
        public Contract createFromParcel(Parcel in) {
            return new Contract(in);
        }

        @Override
        public Contract[] newArray(int size) {
            return new Contract[size];
        }
    };
}

