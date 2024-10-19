import { Box, styled, Typography } from '@mui/material'

const LogoText = styled(Typography)({
    fontSize: "1.2rem",
    textAlign: "start",
    display: "inline-block",

})

export const SiteLogo = ({ size }: { size?: string }) => {
    return (
        <Box sx={{ userSelect: "none", display: "inline-block" }}>
            <LogoText color='#DB5356' fontSize={size}>Health</LogoText>
            <LogoText color='MenuText' fontSize={size}>Sphere</LogoText>
        </Box>
    )
}

export default SiteLogo

export const PageTitle = styled(Typography)({
    fontSize: '1.5rem',
    fontWeight: '600',
    lineHeight: '1.5em'
});

export const PageSubtitle = styled(Typography)({
    fontSize: '1em',
    marginBottom: '16px',
});